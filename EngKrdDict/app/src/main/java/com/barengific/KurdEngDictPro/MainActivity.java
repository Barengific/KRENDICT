package com.barengific.KurdEngDictPro;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;

import android.os.Handler;
import android.widget.Button;
import android.widget.SearchView;

import java.util.List;
//import com.google.android.gms.ads.MobileAds;
//import com.google.android.gms.ads.initialization.InitializationStatus;
//import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdView;

public class MainActivity extends AppCompatActivity {
    private static final VocabAdapter adapter = new VocabAdapter();

    private VocabRepository repository;
    private Handler timeout = new Handler();
    private String searchText;
//    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        MobileAds.initialize(this, new OnInitializationCompleteListener() {
//            @Override
//            public void onInitializationComplete(InitializationStatus initializationStatus) {
//            }
//        });
//        mAdView = findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);

        final Button buLanguage = findViewById(R.id.buLanguage);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final SearchView searchView = findViewById(R.id.search_view);

        recyclerView.setAdapter(adapter);

        repository = new VocabRepository(this.getApplication());

        searchView.setIconified(false);

        if (searchView.getQuery().toString().trim().length() == 0) {
            database();
        } else
            database(searchView.getQuery().toString(), buLanguage.getText().toString());

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchText = newText;

                Runnable mRunnable = new Runnable() {
                    @Override
                    public void run() {
                        if (searchText.trim().length() == 0) {
                            database();
                        } else {
                            String language = buLanguage.getText().toString();
                            database(searchText, language);
                        }
                    }
                };
                timeout.removeCallbacksAndMessages(null);
                timeout.postDelayed(mRunnable, 250);

                return true;
            }
        });

        adapter.setOnClickListener(new VocabAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Vocab vocab) {
                Intent intent = new Intent(MainActivity.this, AnkiSend.class);
                intent.putExtra(AnkiSend.EXTRA_WORD, vocab.getWord());
                intent.putExtra(AnkiSend.EXTRA_MEANING, vocab.getMeaning());
                intent.putExtra(AnkiSend.EXTRA_LANGUAGE, vocab.getLanguage());

                if (!vocab.getHistory()) {
                    String word = vocab.getWord();
                    String meaning = vocab.getMeaning();
                    String language = vocab.getLanguage();
                    int id = vocab.getId();
                    Vocab vocabUpdated = new Vocab(word, meaning, language, true);
                    vocabUpdated.setId(id);
                    repository.update(vocabUpdated);
                }

                MainActivity.this.startActivityForResult(intent, 1);
            }
        });

        String language = "eng-krd";
        buLanguage.setText(language);
        if (searchView.getQuery().toString().trim().length() == 0) {
            database();
        } else {
            database(searchView.getQuery().toString(), language);
        }

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }
//
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                Vocab vocab = adapter.getVocabAt(viewHolder.getAdapterPosition());
                String word = vocab.getWord();
                String meaning = vocab.getMeaning();
                String language = vocab.getLanguage();
                Boolean history = vocab.getHistory();
                int id = vocab.getId();
                Vocab vocabUpdated = new Vocab(word, meaning, language, !history);
                vocabUpdated.setId(id);
                repository.update(vocabUpdated);
                String str = searchView.getQuery().toString();
                if (str.isEmpty())
                    database();
                else
                    database(str, buLanguage.getText().toString());
            }
        }).attachToRecyclerView(recyclerView);
    }

    private void database() {
        repository.getAllVocabs();
    }

    private void database(String word, String language) {
        if (language.equals("All"))
            language = "%";
        word += "%";
        repository.getAllSearchedVocabs(word, language);
    }

    public static void updateAdapter(List<Vocab> vocabs) {
        adapter.setVocabs(vocabs);
    }

}