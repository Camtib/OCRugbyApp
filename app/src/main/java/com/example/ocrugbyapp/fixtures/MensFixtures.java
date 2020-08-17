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


        return view;
        
    }
}