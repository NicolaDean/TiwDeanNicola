package it.polimi.tiw.managment;

import com.google.gson.Gson;

import java.util.List;

public abstract class JsonSerializable {

    public String toJson()
    {
        Gson gson = new Gson();
        return  gson.toJson(this);
    }

    public static<T> String listSerializartion(List<T> list)
    {
        Gson gson = new Gson();
        return  gson.toJson(list);
    }
}
