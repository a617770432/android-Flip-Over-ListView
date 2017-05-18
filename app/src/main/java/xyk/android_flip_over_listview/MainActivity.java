package xyk.android_flip_over_listview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FlipOverListView flv;
    private ArrayList<String> strList;
    private int page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flv = (FlipOverListView) this.findViewById(R.id.flv);
        strList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            strList.add("德玛西亚万岁" + i);
        }
        flv.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, strList));
        View loadingView = LayoutInflater.from(this).inflate(R.layout.lv_footer_view,null);
        final View lastView = LayoutInflater.from(this).inflate(R.layout.lv_last_page_view,null);
        flv.setLoadingView(loadingView);

        flv.setOnFilpOverListener(new FlipOverListView.FilpOverListener() {
            @Override
            public boolean filpOverEvent() {
                Log.d("Check","filpOverEvent");
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        for (int i = 0; i < 20; i++) {
                            strList.add("啦啦啦" + i);
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(page == 3)
                                {
                                    flv.showEndPageView(lastView);
                                    return;
                                }

                                ListAdapter mListAdater =  flv.getAdapter();
                                ((ArrayAdapter)mListAdater).notifyDataSetChanged();
                                flv.setLocked(false);
                                page++;
                            }
                        });
                    }
                }.start();


                return true;
            }
        });
    }
}
