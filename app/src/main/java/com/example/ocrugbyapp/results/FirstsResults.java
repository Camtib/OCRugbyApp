package com.example.ocrugbyapp.results;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.fragment.app.Fragment;

import com.example.ocrugbyapp.R;


public class FirstsResults extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_firsts_results, container, false);

        WebView firstWebView = (WebView) view.findViewById(R.id.webView1);

        WebSettings webSettings = firstWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        firstWebView.loadUrl("https://www.englandrugby.com/fixtures-and-results/search-results?team=15615&season=2019-2020#table");

        return view;
    }
}