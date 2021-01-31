package com.ivan.boldyrev;

import java.util.List;

public class Result{
    private List<Post> result;
    private String totalCount;

    public List<Post> getPosts() {
        return result;
    }

    public String getTotalCount() {
        return totalCount;
    }
}
