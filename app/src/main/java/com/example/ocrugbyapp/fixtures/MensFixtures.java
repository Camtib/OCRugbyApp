package com.example.ocrugbyapp.fixtures;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ocrugbyapp.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;


public class MensFixtures extends Fragment {
    private static final String TAG = "MenFixtures";

    private RecyclerView mRecyclerView;
    AsyncHttpClient client;
    Workbook workbook;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mens_fixtures, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewMensFixtures);

        String url = "http://www.ocrfc.com/images/forms/OC_Fix_18-19.xls";

        client = new AsyncHttpClient();
        client.get(url, new FileAsyncHttpResponseHandler(getActivity()) {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                Toast.makeText(getActivity(), "Download Failed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, File file) {
                Toast.makeText(getActivity(), "Downloaded Successfully", Toast.LENGTH_SHORT).show();
                WorkbookSettings ws = new WorkbookSettings();
                ws.setGCDisabled(true);
                String firstsFixture;
                String secondsFixture;
                String bsFixture;

                final ArrayList<MensFixtureCard> mensFixtures = new ArrayList<>();

                if (file != null) {
                    try {
                        workbook = Workbook.getWorkbook(file);
                        Sheet sheet = workbook.getSheet(1);
                        for (int i = 3 ; i < sheet.getRows() ; i++) {

                            String date = sheet.getCell(1, i).getContents();
                            if (sheet.getCell(4, i).getContents() != "") {
                                firstsFixture = "1st XV vs " + sheet.getCell(4, i).getContents();
                            }
                            else {
                                firstsFixture = "No Fixture";
                            }

                            if (sheet.getCell(8, i).getContents() != "") {
                                secondsFixture = "2nd XV vs " + sheet.getCell(4, i).getContents();
                            }
                            else {
                                secondsFixture = "No Fixture";
                            }

                            if (sheet.getCell(12, i).getContents() != "") {
                                bsFixture = "b XV vs " + sheet.getCell(4, i).getContents();
                            }
                            else {
                                bsFixture = "No Fixture";
                            }

                            String firstsHA = sheet.getCell(5, i).getContents();
                            String secondsHA = sheet.getCell(9, i).getContents();
                            String bsHA = sheet.getCell(13, i).getContents();

                            mensFixtures.add(new MensFixtureCard(date, firstsFixture, secondsFixture, bsFixture, firstsHA, secondsHA, bsHA));
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (BiffException e) {
                        e.printStackTrace();
                    }
                }

                MensFixturesListAdapter adapter = new MensFixturesListAdapter(getActivity(), R.layout.cardview_mens_fixtures, mensFixtures);
                mListView.setAdapter(adapter);
            }
        });








/*
        Document doc = null;
        Document doc2 = null;
        Document doc3 = null;
        try {
            doc = Jsoup.connect("http://www.ocrfc.com/index.php/oc-fixtures.html").get();
            doc2 = Jsoup.connect("http://www.ocrfc.com/index.php/2nd-xv-fixtures.html").get();
            doc3 = Jsoup.connect("http://www.ocrfc.com/index.php/b-xv-fixtures.html").get();
            //Find table element
            Elements firstsFixtureTables = doc.getElementsByTag("table");
            Elements secondsFixtureTables = doc2.getElementsByTag("table");
            Elements bsFixtureTables = doc3.getElementsByTag("table");

            Elements rows = firstsFixtureTables.select("tr");
            Elements rows2 = secondsFixtureTables.select("tr");
            Elements rows3 = bsFixtureTables.select("tr");

            for (Element row : rows) {
                Elements cells = row.select("td");
                for (Element cell : cells) {
                    String date = cell.select("td[Width : 63px]").text();
                    String firstsFixture = "1st XV vs " + cell.select("td[Width : 213px]").text();
                    String firstsKOTime = cell.select("td[Width : 80px]").text();
                    String firstsHA = cell.select("td[Width : 57px]").text();
                }
            }
            //need different way to select cells
            for (Element row2 : rows2) {
                Elements cells = row.select("td");
                for (Element cell : cells) {
                    String date2 = cell.select("td[Width : 63px]").text();
                    String secondsFixture = "2nd XV vs " + cell.select("td[Width : 213px]").text();
                    String secondsKOTime = cell.select("td[Width : 80px]").text();
                    String secondsHA = cell.select("td[Width : 57px]").text();
                }
            }
            //need different way to select cells
            for (Element row3 : rows3) {
                Elements cells = row.select("td");
                for (Element cell : cells) {
                    String date3 = cell.select("td[Width : 63px]").text();
                    String bsFixture = "1st XV vs " + cell.select("td[Width : 213px]").text();
                    String bsKOTime = cell.select("td[Width : 80px]").text();
                    String bsHA = cell.select("td[Width : 57px]").text();
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        //or might be easier to get the admin people to input fixtures manually? as tables and elements not got ids.

 */


        /*
         * find row elements
         * loop through rows
         * find relevant info using if else statements
         * add relevant info from row to new MensFixtureCard
         * continue loop through rows
         * add info from three docs into different fixture cards
         */

        return view;


    }
}