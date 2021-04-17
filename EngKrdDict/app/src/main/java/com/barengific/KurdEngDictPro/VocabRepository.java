package com.barengific.KurdEngDictPro;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class VocabRepository {
    private VocabDao vocabDao;

    public VocabRepository(Application application) {
        VocabDatabase database = VocabDatabase.getInstance(application);
        vocabDao = database.vocabDao();
    }

    public void update(Vocab vocab) {
        new UpdateVocabAsyncTask(vocabDao).execute(vocab);
    }

    public void getAllSearchedVocabs(String search, String language) {
        new GetSearchedVocabsAsyncTask(vocabDao).execute(search, language);
    }

    public void getAllVocabs() {
        new GetAllVocabsAsyncTask(vocabDao).execute();
    }

    private static class GetAllVocabsAsyncTask extends AsyncTask<Void, Void, List<Vocab>> {
        private VocabDao vocabDao;

        private GetAllVocabsAsyncTask(VocabDao vocabDao) {
            this.vocabDao = vocabDao;
        }

        @Override
        protected List<Vocab> doInBackground(Void... voids) {
            return vocabDao.getAllVocabs();
        }

        @Override
        protected void onPostExecute(List<Vocab> vocabs) {
            super.onPostExecute(vocabs);
            MainActivity.updateAdapter(vocabs);
        }
    }

    private static class GetSearchedVocabsAsyncTask extends AsyncTask<String, Void, List<Vocab>> {
        private VocabDao vocabDao;

        private GetSearchedVocabsAsyncTask(VocabDao vocabDao) {
            this.vocabDao = vocabDao;
        }

        @Override
        protected List<Vocab> doInBackground(String... strings) {
            return vocabDao.getSearchedVocabs(strings[0],strings[1]);
        }

        @Override
        protected void onPostExecute(List<Vocab> vocabs) {
            super.onPostExecute(vocabs);
            MainActivity.updateAdapter(vocabs);
        }
    }

    private static class UpdateVocabAsyncTask extends AsyncTask<Vocab, Void, Void> {
        private VocabDao vocabDao;

        private UpdateVocabAsyncTask(VocabDao vocabDao) {
            this.vocabDao = vocabDao;
        }

        @Override
        protected Void doInBackground(Vocab... vocabs) {
            vocabDao.update(vocabs[0]);
            return null;
        }
    }
}