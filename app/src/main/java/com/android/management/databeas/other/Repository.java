package com.android.management.databeas.other;

import android.app.Application;

import androidx.lifecycle.LiveData;

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

import java.util.List;

public class Repository {

    UserDAO userDAO;
    CenterDAO centerDAO;
    BranchDAO branchDAO;
    EpisodesDAO episodesDAO;
    TaskDAO taskDAO;
    AdsDAO adsDAO;

    public Repository(Application application) {
        MyDatabase db = MyDatabase.getDatabase(application);
        userDAO = db.userDAO();
        centerDAO = db.centerDAO();
        branchDAO = db.branchDAO();
        episodesDAO = db.episodesDAO();
        taskDAO = db.taskDAO();
        adsDAO = db.adsDAO();
    }

    // TODO : USER Queries

    public long insertUser(User users) {
        return userDAO.insertUser(users);
    }

    public int updateUser(User users) {
        return userDAO.updateUser(users);
    }

    public int changePassword(int id, String password) {
        return userDAO.changePassword(id, password);
    }

    public int deleteUser(User users) {
        return userDAO.deleteUser(users);
    }

    public int deleteUserByName(String name) {
        return userDAO.deleteUserByName(name);
    }

    public boolean isUserIdExist(String p_id) {
        return userDAO.isUserIdExist(p_id);
    }

    public boolean isUserEmailExist(String email) {
        return userDAO.isUserEmailExist(email);
    }

    public User login(String p_id, String password) {
        return userDAO.login(p_id, password);
    }

    public LiveData<List<User>> getAdmins() {
        return userDAO.getAdmins();
    }

    public LiveData<List<User>> getManagers() {
        return userDAO.getManagers();
    }

    public List<String> getManagersName() {
        return userDAO.getManagersName();
    }

    public List<String> getAdminsName() {
        return userDAO.getAdminsName();
    }

    public List<String> getAllWalletsName() {
        return userDAO.getAllWalletsName();
    }

    public LiveData<List<User>> getStudents() {
        return userDAO.getStudents();
    }

    public LiveData<List<User>> getStudentsByEpisodes(String episodeName) {
        return userDAO.getStudentsByEpisodes(episodeName);
    }

    public LiveData<List<User>> getWalletsByEpisodes(String episodeName) {
        return userDAO.getWalletsByEpisodes(episodeName);
    }

    public LiveData<List<User>> getWallets() {
        return userDAO.getWallets();
    }

    public User getStudentsByName(String name) {
        return userDAO.getStudentsByName(name);
    }

    // TODO : Center Queries

    public long insertCenter(Center model) {
        return centerDAO.insertCenter(model);
    }

    public int updateCenter(Center model) {
        return centerDAO.updateCenter(model);
    }

    public int deleteCenter(Center model) {
        return centerDAO.deleteCenter(model);
    }

    public LiveData<List<Center>> getAllCenter() {
        return centerDAO.getAllCenter();
    }

    public List<String> getAllCenterName() {
        return centerDAO.getAllCenterName();
    }

    // TODO : Branch Queries

    public long insertBranch(Branch model) {
        return branchDAO.insertBranch(model);
    }

    public int updateBranch(Branch model) {
        return branchDAO.updateBranch(model);
    }

    public int deleteBranch(Branch model) {
        return branchDAO.deleteBranch(model);
    }

    public LiveData<List<Branch>> getAllBranch() {
        return branchDAO.getAllBranch();
    }

    public List<String> getAllBranchName() {
        return branchDAO.getAllBranchName();
    }

    // TODO : Episodes Queries

    public long insertEpisodes(Episodes model) {
        return episodesDAO.insertEpisodes(model);
    }

    public int updateEpisodes(Episodes model) {
        return episodesDAO.updateEpisodes(model);
    }

    public int deleteEpisodes(Episodes model) {
        return episodesDAO.deleteEpisodes(model);
    }

    public LiveData<List<Episodes>> getAllEpisodes() {
        return episodesDAO.getAllEpisodes();
    }

    public LiveData<List<Episodes>> getEpisodesByCenter(String centerName) {
        return episodesDAO.getEpisodesByCenter(centerName);
    }

    public LiveData<List<Episodes>> getEpisodesByAdmin(String adminName) {
        return episodesDAO.getEpisodesByAdmin(adminName);
    }

    public List<String> getAllEpisodesName() {
        return episodesDAO.getAllEpisodesName();
    }

    // TODO : Task Queries

    public long insertTask(Task model) {
        return taskDAO.insertTask(model);
    }

    public int updateTask(Task model) {
        return taskDAO.updateTask(model);
    }

    public int deleteTask(Task model) {
        return taskDAO.deleteTask(model);
    }

    public LiveData<List<Task>> getAllTask() {
        return taskDAO.getAllTask();
    }

    public LiveData<List<Task>> getAllTaskByName(String student_name) {
        return taskDAO.getAllTaskByName(student_name);
    }

    // TODO : Task Queries

    public long insertAds(Ads model) {
        return adsDAO.insertAds(model);
    }

    public int updateAds(Ads model) {
        return adsDAO.updateAds(model);
    }

    public int deleteAds(Ads model) {
        return adsDAO.deleteAds(model);
    }

    public LiveData<List<Ads>> getAllAds() {
        return adsDAO.getAllAds();
    }

    public LiveData<List<Ads>> getAllAdsByCenter(String student_name) {
        return adsDAO.getAllAdsByCenter(student_name);
    }

}
