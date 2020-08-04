package com.example.ocrugbyapp.fixtures;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ocrugbyapp.R;

import java.util.ArrayList;

public class WomensFixtures extends Fragment {
    private static final String TAG = "WomensFixtures";

    private ListView mListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_womens_fixtures, container, false);
        mListView = (ListView) view.findViewById(R.id.womensListView);

        ArrayList<WomensFixtureCard> womensFixtures = new ArrayList<>();

        womensFixtures.add(new WomensFixtureCard("", "no fixtures yet", "", ""));


        WomensFixturesListAdapter adapter = new WomensFixturesListAdapter(getActivity(), R.layout.cardview_womens_fixtures, womensFixtures);
        mListView.setAdapter(adapter);

        return view;
    }

    /*
     * no fixtures posted yet, need to check if fixtures are going to be posted.

     Document doc = null;

     try {
     doc = Jsoup.connect("http://www.ocrfc.com/index.php/oc-fixtures.html").get();

     //Find table element
     Elements firstsFixtureTables = doc.getElementsByTag("table");


     Elements rows = firstsFixtureTables.select("tr");


     for (Element row : rows) {
     Elements cells = row.select("td");
     for (Element cell : cells) {
     String date = cell.select("td[Width : 63px]").text();
     String firstsFixture = "1st XV vs " + cell.select("td[Width : 213px]").text();
     String firstsKOTime = cell.select("td[Width : 80px]").text();
     String firstsHA = cell.select("td[Width : 57px]").text();
     }
     }

     } catch (IOException e) {
     e.printStackTrace();
     }
     */


     /*
     * find row elements
     * loop through rows
     * find relevant info using if else statements
     * add relevant info from row to new MensFixtureCard
     * continue loop through rows
     * add info from three docs into different fixture cards
     */
}