package com.example.movieapp.main.helper;

import static com.example.movieapp.common.constants.PagePaths.DATA_PARAM_KEY;
import static com.example.movieapp.common.constants.PagePaths.DETAIL_PAGE_PATH;
import static com.example.movieapp.main.event.MainActivityEvents.OPEN_DETAILS_PAGE;

import android.content.Intent;
import android.net.Uri;

import androidx.lifecycle.MutableLiveData;

import com.example.movieapp.base.model.EventData;
import com.example.movieapp.common.util.CommonUtil;
import com.example.movieapp.listing.model.response.MovieResultData;

import java.util.HashMap;

import javax.inject.Inject;

public class IntentParser {

    MutableLiveData<EventData> eventStream = new MutableLiveData<>();

    HashMap<String, PageName> pathToEventsMap = new HashMap<>();

    @Inject
    public IntentParser() {
        initPathMap();
    }

    void initPathMap() {
        pathToEventsMap.put(DETAIL_PAGE_PATH, PageName.MOVIE_DETAIL);
    }

    public MutableLiveData<EventData> getEventStream() {
        return eventStream;
    }

    public void parseIntent(Intent intent) {
        String action = intent.getAction();
        Uri data = intent.getData();
        if (!action.equals(Intent.ACTION_VIEW) || data == null) {
            return;
        }
        redirectToPath(data);
    }

    private void redirectToPath(Uri data) {
        String path = data.getPath();
        String query = data.getQuery();
        PageName pageName = pathToEventsMap.get(path);
        String eventId = getEventId(pageName);
        Object eventData = getEventData(pageName, query);
        if (eventId != null) {
            eventStream.postValue(new EventData(eventId, eventData));
        }
    }

    private Object getEventData(PageName pageName, String query) {
        switch (pageName) {
            case MOVIE_DETAIL: return getDetailPageData(query);
            default: return null;
        }
    }

    private MovieResultData getDetailPageData(String query) {
        HashMap<String, String> paramsMap = getParamsMap(query);
        String dataParam = paramsMap.get(DATA_PARAM_KEY);
        String json = Uri.decode(dataParam);
        return CommonUtil.convertToMovieResultData(json);
    }

    private HashMap<String, String> getParamsMap(String query) {
        String[] params = query.split("&");
        HashMap<String, String> paramsMap = new HashMap<>();
        for (String param : params) {
            String[] values = param.split("=");
            paramsMap.put(values[0], values[1]);
        }
        return paramsMap;
    }

    private String getEventId(PageName pageName) {
        switch (pageName) {
            case MOVIE_DETAIL: return OPEN_DETAILS_PAGE;
            default: return null;
        }
    }

    enum PageName {
        MOVIE_DETAIL
    }

}
