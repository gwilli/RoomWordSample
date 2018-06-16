package io.atomicrobot.greg.roomwordsample.data;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import io.atomicrobot.greg.roomwordsample.data.dao.WordDao;
import io.atomicrobot.greg.roomwordsample.data.entity.Word;

@Database(entities = {Word.class}, version = 1)
public abstract class WordRoomDatabase extends RoomDatabase{
    private static WordRoomDatabase instance;

    public static WordRoomDatabase getDatabase(final Context context) {
        if (instance == null) {
            synchronized (WordRoomDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            WordRoomDatabase.class, "word_database")
                            .addCallback(roomDatabaseCallback)
                            .build();
                }
            }
        }

        return instance;
    }
    public abstract WordDao wordDao();

    private static RoomDatabase.Callback roomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(instance).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private WordDao wordDao;

        public PopulateDbAsync(WordRoomDatabase db) {
            wordDao = db.wordDao();
        }

        @Override
        protected Void doInBackground(Void... params) {
            wordDao.deleteAll();
            wordDao.insertWord(new Word("Hello"));
            wordDao.insertWord(new Word("World"));
            return null;
        }
    }
}
