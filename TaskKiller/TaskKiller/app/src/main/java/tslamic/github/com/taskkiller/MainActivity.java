package tslamic.github.com.taskkiller;

import android.app.ActivityManager;
import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends ListActivity implements AdapterView.OnItemClickListener, SweepListener {

    private List<ProcessHolder> mRunningApps;
    private Set<String> mKillablePackages;
    private ActivitiesAdapter mAdapter;
    private ProgressBar mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mProgress = (ProgressBar) findViewById(R.id.progress);
        mProgress.setVisibility(View.GONE);

        mRunningApps = getAvailableLaunchersCurrentlyRunning();
        mAdapter = new ActivitiesAdapter();
        setListAdapter(mAdapter);

        final ListView view = getListView();
        view.setOnItemClickListener(this);

        new SweepSelectorTask(this).execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mAdapter.kill(position);
    }

    private class ActivitiesAdapter extends BaseAdapter {

        private boolean mFilter;

        @Override
        public int getCount() {
            return mRunningApps.size();
        }

        @Override
        public Object getItem(int position) {
            return mRunningApps.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (null == convertView) {
                convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.list_item, parent, false);
            }

            final ProcessHolder holder = mRunningApps.get(position);
            final TextView tv = (TextView) convertView;

            final Drawable icon = holder.icon;
            icon.setBounds(0, 0, 48, 48);
            tv.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);

            if (mFilter && canKill(holder.packageName)) {
                tv.setTextColor(Color.GREEN);
            } else {
                tv.setTextColor(Color.WHITE);
            }
            tv.setText(holder.label);

            return tv;
        }

        @Override
        public void notifyDataSetChanged() {
            mFilter = (null != mKillablePackages);
            super.notifyDataSetChanged();
        }

        @Override
        public boolean isEnabled(int position) {
            return canKill(position);
        }

        boolean canKill(final int position) {
            boolean canKill = false;

            if (mFilter) {
                final ProcessHolder holder = mRunningApps.get(position);
                final String packageName = holder.packageName;
                canKill = canKill(packageName);
            }

            return canKill;
        }

        boolean canKill(final String packageName) {
            return mFilter && mKillablePackages.contains(packageName);
        }

        void kill(final int position) {
            final ProcessHolder holder = mRunningApps.get(position);
            final String packageName = holder.packageName;
            final ActivityManager manager = getActivityManager();
            manager.killBackgroundProcesses(packageName);
            mRunningApps.remove(position);
            notifyDataSetChanged();
        }

    }

    /**
     * Returns the list of all available launcher Activities.
     */
    private List<ResolveInfo> getAvailableActivityLaunchers() {
        final PackageManager manager = getPackageManager();
        final Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        return manager.queryIntentActivities(intent, 0);
    }

    /*
     * Create a mapping from a process name to its ResolveInfo.
     */
    private Map<String, ResolveInfo> getAvailableActivityLaunchersMap() {
        final List<ResolveInfo> list = getAvailableActivityLaunchers();

        final HashMap<String, ResolveInfo> map = new HashMap<String, ResolveInfo>(list.size());
        for (ResolveInfo info : list) {
            map.put(info.activityInfo.processName, info);
        }

        return map;
    }

    /*
     * Return a filtered list of currently running Activities.
     */
    private List<ProcessHolder> getAvailableLaunchersCurrentlyRunning() {
        final ActivityManager manager = getActivityManager();
        final Map<String, ResolveInfo> map = getAvailableActivityLaunchersMap();

        final List<ActivityManager.RunningAppProcessInfo> runningApps = manager.getRunningAppProcesses();
        final List<ProcessHolder> available = new ArrayList<ProcessHolder>(runningApps.size());

        final PackageManager packageManager = getPackageManager();
        for (ActivityManager.RunningAppProcessInfo runningApp : runningApps) {
            if (!skip(runningApp)) { // skips system-related Activities
                final ResolveInfo info = map.get(runningApp.processName);
                if (null != info) {
                    available.add(new ProcessHolder(runningApp.pid, packageManager, info));
                }
            }
        }
        return available;
    }

    /**
     * Some of the filters applied by the Advanced Task Killer
     */
    private static boolean skip(final ActivityManager.RunningAppProcessInfo info) {
        return info == null ||
                info.processName == null ||
                (info.processName.equalsIgnoreCase("system")) ||
                (info.processName.equalsIgnoreCase("android.process.acore")) ||
                (info.processName.equalsIgnoreCase("android.process.media")) ||
                (info.processName.equalsIgnoreCase("com.android.phone")) ||
                (info.processName.equalsIgnoreCase("com.android.bluetooth")) ||
                (info.processName.startsWith("com.android.inputmethod")) ||
                (info.importance <= 300);
    }

    private ActivityManager getActivityManager() {
        return (ActivityManager) getSystemService(ACTIVITY_SERVICE);
    }

    @Override
    public List<ProcessHolder> getRunningApps() {
        return mRunningApps;
    }

    @Override
    public void onSweepStart() {
        mProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSweepFinish(Set<String> packages) {
        mKillablePackages = packages;
        mAdapter.notifyDataSetChanged();
        mProgress.setVisibility(View.GONE);
    }

}
