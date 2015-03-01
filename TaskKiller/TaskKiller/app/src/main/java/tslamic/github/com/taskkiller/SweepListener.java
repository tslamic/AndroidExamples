package tslamic.github.com.taskkiller;

import java.util.List;
import java.util.Set;

interface SweepListener {

    List<ProcessHolder> getRunningApps();

    void onSweepStart();

    void onSweepFinish(Set<String> packages);

}
