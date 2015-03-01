package tslamic.github.com.taskkiller;

import android.os.AsyncTask;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class SweepSelectorTask extends AsyncTask<Void, Integer, Set<String>> {

    // Processes only hosting activities that are not visible, so it can be killed
    // without any disruption.
    private static final int CACHED_APP_MIN_ADJ = 9;

    // The system process runs at the default adjustment.
    private static final int SYSTEM_ADJ = -16;

    private final SweepListener mListener;

    public SweepSelectorTask(SweepListener listener) {
        if (null == listener) {
            throw new IllegalArgumentException("listener is null");
        }
        mListener = listener;
    }

    @Override
    protected void onPreExecute() {
        mListener.onSweepStart();
    }

    @Override
    protected Set<String> doInBackground(Void... params) {
        final List<ProcessHolder> runningApps = mListener.getRunningApps();
        final Set<String> emptyApps = new HashSet<String>(runningApps.size());
        for (ProcessHolder runningApp : runningApps) {
            if (getOomParameter(runningApp.pid) >= CACHED_APP_MIN_ADJ) {
                emptyApps.add(runningApp.packageName);
            }
        }
        return emptyApps;
    }

    @Override
    protected void onPostExecute(Set<String> packages) {
        mListener.onSweepFinish(packages);
    }

    private int getOomParameter(final int pid) {
        int oomParam = SYSTEM_ADJ;

        InputStream stdInput = null;
        try {
            final String catCommand = String.format("../proc/%s/oom_adj", pid);
            final Process process = new ProcessBuilder("cat", catCommand).start();
            stdInput = process.getInputStream();

            final int exitValue = process.waitFor();
            if (exitValue == 0) {
                oomParam = readNumberFrom(stdInput);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            close(stdInput);
        }

        return oomParam;
    }

    private static int readNumberFrom(InputStream stream) throws IOException {
        int value = SYSTEM_ADJ;

        final byte[] buffer = new byte[32];
        if (stream.read(buffer) > 0) {
            value = extractNextNumberFromBuffer(buffer);
        }

        return value;
    }

    private static int extractNextNumberFromBuffer(final byte[] buffer) {
        int r = 0;
        int i = 0;
        int p = 1;

        int c;
        while ((c = Character.getNumericValue(buffer[i++])) >= 0) {
            r = (p * r) + c;
            p *= 10;
        }

        return r;
    }

    private static void close(final Closeable c) {
        if (null != c) {
            try {
                c.close();
            } catch (IOException e) {
                // Ignore
            }
        }
    }

}
