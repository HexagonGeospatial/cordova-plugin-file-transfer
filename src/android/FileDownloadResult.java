package org.apache.cordova.filetransfer;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileDownloadResult {

    private JSONObject fileEntry = null;
    private Map<String, List<String>> headers = null;

    public FileDownloadResult(JSONObject fileEntry, HttpURLConnection connection) {
        this.fileEntry = fileEntry;

        if (connection != null) {
            this.headers = connection.getHeaderFields();
        } else {
            this.headers = new HashMap<>();
        }
    }

    public JSONObject getFileEntry() {
        return fileEntry;
    }

    public void setFileEntry(JSONObject fileEntry) {
        this.fileEntry = fileEntry;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, List<String>> headers) {
        this.headers = headers;
    }

    private JSONObject headersToJSON() throws JSONException {
        JSONObject json = new JSONObject();

        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
                if (entry.getKey() != null) {
                    StringBuilder commaSeparatedValues = new StringBuilder();

                    for (String value: entry.getValue()) {
                        commaSeparatedValues.append(value).append(",");
                    }

                    commaSeparatedValues.setLength(Math.max(commaSeparatedValues.length() - 1, 0));
                    json.put(entry.getKey(), commaSeparatedValues.toString());
                }
            }
        }

        return json;
    }

    public JSONObject toJSONObject() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("fileEntry", fileEntry);
        json.put("headers", headersToJSON());
        return json;
    }
}
