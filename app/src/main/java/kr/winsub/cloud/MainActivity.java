package kr.winsub.cloud;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    ProgressBar loadingProgress;
    String parse_url = "https://cloud.winsub.kr/";
    ArrayList<String> urls = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mRecyclerView = findViewById(R.id.list);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainPageTask asyncTask = new MainPageTask();
                asyncTask.execute();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        loadingProgress = findViewById(R.id.loadingProgress);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_home);

        MainPageTask asyncTask = new MainPageTask();
        asyncTask.execute();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if ((urls.size()) != 0) {
            reload(urls.get(urls.size() - 1), false);
            urls.remove(urls.size() - 1);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_info) {
            Intent intent = new Intent(getBaseContext(), InfoActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void reload(String url, boolean isNew) {
        loadingProgress.setVisibility(View.VISIBLE);
        if (isNew)
            urls.add(parse_url);
        parse_url = url;
        MainPageTask asyncTask = new MainPageTask();
        asyncTask.execute();

        System.out.println(urls.size());
    }

    private class MainPageTask extends AsyncTask<String, Object, Void> {
        ArrayList<Lists> parsed = new ArrayList<>();
        private Elements name;
        private Elements list;
        private Elements size;
        private Elements icon;

        @Override
        protected void onPostExecute(Void result) {
            ArrayList<Lists> ListArrayList = new ArrayList<>();
            ListAdapter myAdapter = new ListAdapter(ListArrayList, MainActivity.this);
            mRecyclerView.setAdapter(myAdapter);
            ListArrayList.addAll(parsed); // Add parsed's values to Real array list
            loadingProgress.setVisibility(View.GONE);
            super.onPostExecute(result);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... params) {
            //백그라운드 작업이 진행되는 곳.
            String notice_url = parse_url;
            try {
                Document doc = Jsoup.connect(notice_url).get();
                list = doc.select("#bs-table > tbody > tr"); // Get notice list

                for (Element item : list) { // loop
                    name = item.select("td:nth-child(1) > a"); // Get File Name
                    size = item.select("td:nth-child(2)"); // Get File Size
                    icon = item.select("td:nth-child(1) > span"); // Get item icon
                    String file_url = name.attr("abs:href"); // Parse REAL url(href)

                    publishProgress(name.text(), size.text(), file_url, icon.attr("class"), name.size()); // Send it!
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassCastException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Object... params) { // Receive from doInBackground
            String name = (String) params[0];
            String size = (String) params[1];
            String url = (String) params[2];
            String icon_class = (String) params[3];
            int isEmpty = (int) params[4];
            boolean isFile = false;
            if (icon_class.equals("glyphicon glyphicon-folder-close"))
                isFile = false;
            else if (icon_class.equals("glyphicon glyphicon-file"))
                isFile = true;

            if (isEmpty == 0) {
                size = url = "";
                name = "폴더가 비어있습니다!";
            }
            parsed.add(new Lists(name, size, url, isFile)); // Add values to array list
        }
    }
}
