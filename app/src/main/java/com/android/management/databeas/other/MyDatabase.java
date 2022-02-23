package com.android.management.databeas.other;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.android.management.databeas.DAO.AdsDAO;
import com.android.management.databeas.DAO.BranchDAO;
import com.android.management.databeas.DAO.CenterDAO;
import com.android.management.databeas.DAO.EpisodesDAO;
import com.android.management.databeas.DAO.TaskDAO;
import com.android.management.databeas.DAO.UserDAO;
import com.android.management.model.Ads;
import com.android.management.model.Branch;
import com.android.management.model.Center;
import com.android.management.model.Episodes;
import com.android.management.model.Task;
import com.android.management.model.User;
import com.android.management.model.Validity;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Branch.class, User.class, Center.class, Episodes.class, Task.class, Ads.class},
        version = 1, exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {

    public abstract UserDAO userDAO();

    public abstract CenterDAO centerDAO();

    public abstract BranchDAO branchDAO();

    public abstract EpisodesDAO episodesDAO();

    public abstract TaskDAO taskDAO();

    public abstract AdsDAO adsDAO();

    private static volatile MyDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static MyDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (MyDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MyDatabase.class, "management_db")
                            .addCallback(callback)
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback callback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                insertManager();
                insertCenter();
                insertBranch();
                insertEpisodes();
                insertTask();
                insertAds();
            });
        }
    };

    private static void insertManager() {
        ArrayList<User> list = new ArrayList<>();
        list.add(new User("1", "احمد", "a@a.a", "0592440530",
                Calendar.getInstance().getTime(), "غزة النصر", "غزة",
                "مركز علي بن ابي طالب", "", "1", Validity.Manager, ""));

        list.add(new User("2", "محمد", "m@m.m", "12",
                Calendar.getInstance().getTime(), "غزة النصر", "غزة",
                "مركز علي بن ابي طالب", "", "2", Validity.Admin, ""));

        list.add(new User("3", "خليل", "k@k.k", "123",
                Calendar.getInstance().getTime(), "غزة النصر", "غزة",
                "مركز علي بن ابي طالب", "", "3", Validity.Admin, ""));

        list.add(new User("4", "بلال", "b@b.b", "1234",
                Calendar.getInstance().getTime(), "غزة الشجاعية", "غزة",
                "مركز علي بن ابي طالب", "حلقة القدس", "4", Validity.Wallet,
                ""));

        list.add(new User("5", "نادر", "n@n.n", "12345",
                Calendar.getInstance().getTime(), "غزة الشجاعية", "غزة",
                "مركز علي بن ابي طالب", "حلقة القدس", "5", Validity.Student,
                ""));

        INSTANCE.userDAO().insertUser(list);
    }

    private static void insertCenter() {
        Center center = new Center("مركز علي بن ابي طالب", "غزة",
                "", "شارع الرشيد", "15", "أحمد طلال");
        INSTANCE.centerDAO().insertCenter(center);
    }

    private static void insertBranch() {
        ArrayList<Branch> branches = new ArrayList<>();
        branches.add(new Branch("غزة"));
        branches.add(new Branch("البريج"));
        branches.add(new Branch("النصيرات"));
        branches.add(new Branch("المغازي"));
        branches.add(new Branch("ديرالبلح"));
        branches.add(new Branch("خانيونس"));
        branches.add(new Branch("رفح"));
        INSTANCE.branchDAO().insertBranch(branches);
    }

    private static void insertEpisodes() {
        ArrayList<Episodes> branches = new ArrayList<>();
        branches.add(new Episodes("حلقة القدس", "احمد", "مركز علي بن ابي طالب",
                "غزة", "15",
                "حلقة القدس حلقة القدس حلقة القدس حلقة القدس حلقة القدس "
                , "شارع الرشيد", ""));
        INSTANCE.episodesDAO().insertEpisodes(branches);
    }

    private static void insertTask() {
        ArrayList<Task> list = new ArrayList<>();
        list.add(new Task("نادر", "حلقة القدس", "مركز علي بن ابي طالب",
                "بلال", "محمد", Calendar.getInstance().getTime(),
                "15", "30",
                "صفحة", 3, "ملاحظات ملاحظات ملاحظات "));

        list.add(new Task("نادر", "حلقة القدس", "مركز علي بن ابي طالب",
                "بلال", "محمد", Calendar.getInstance().getTime(),
                "100", "155",
                "صفحة", 5, "ملاحظات ملاحظات ملاحظات "));

        list.add(new Task("نادر", "حلقة القدس", "مركز علي بن ابي طالب",
                "بلال", "محمد", Calendar.getInstance().getTime(),
                "60", "90",
                "صفحة", 0, "ملاحظات ملاحظات ملاحظات "));

        INSTANCE.taskDAO().insertTask(list);
    }

    private static void insertAds() {
        ArrayList<Ads> list = new ArrayList<>();
        list.add(new Ads(
                Calendar.getInstance().getTime(),
                "اهلا بك في اعلاننا الجميل", "احمد",
                "مركز علي بن ابي طالب", "حلقة القدس", ""));
        list.add(new Ads(
                Calendar.getInstance().getTime(),
                "اهلا بك في اعلاننا الجميل 2", "احمد", "مركز علي بن ابي طالب",
                "حلقة القدس", ""));

        INSTANCE.adsDAO().insertAds(list);
    }

}
