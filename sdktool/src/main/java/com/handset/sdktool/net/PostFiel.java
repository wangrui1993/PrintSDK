package com.handset.sdktool.net;

public class PostFiel {

    private String res_folder;
    private String res_dfs;
    private String res_id;

    public String getRes_folder() {
        return res_folder;
    }

    public void setRes_folder(String res_folder) {
        this.res_folder = res_folder;
    }

    public String getRes_dfs() {
        return res_dfs;
    }

    public void setRes_dfs(String res_dfs) {
        this.res_dfs = res_dfs;
    }

    public String getRes_id() {
        return res_id;
    }

    public void setRes_id(String res_id) {
        this.res_id = res_id;
    }

    @Override
    public String toString() {
        return "PostFiel{" +
                "res_folder='" + res_folder + '\'' +
                ", res_dfs='" + res_dfs + '\'' +
                ", res_id='" + res_id + '\'' +
                '}';
    }
}
