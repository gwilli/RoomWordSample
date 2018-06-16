package io.atomicrobot.greg.roomwordsample.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import io.atomicrobot.greg.roomwordsample.data.dao.WordDao;
import io.atomicrobot.greg.roomwordsample.data.entity.Word;

public class WordRepository {
    private WordDao wordDao;
    private LiveData<List<Word>> allWords;

    public WordRepository(Application application) {
        WordRoomDatabase db = WordRoomDatabase.getDatabase(application);
        wordDao = db.wordDao();
        allWords = wordDao.getAllWords();
    }

    public LiveData<List<Word>> getAllWords() {
        return allWords;
    }

    public void insert(Word word) {
        new InsertAsyncTask(wordDao).execute(word);
    }


    private static class InsertAsyncTask extends AsyncTask<Word, Void, Void> {
        private WordDao wordDao;
        public InsertAsyncTask(WordDao wordDao) {
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(final Word... words) {
            wordDao.insertWord(words[0]);
            return null;
        }
    }
}
