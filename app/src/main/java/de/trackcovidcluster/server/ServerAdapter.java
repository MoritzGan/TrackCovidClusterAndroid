package de.trackcovidcluster.server;

import android.os.Build;
import android.util.Base64;

import androidx.annotation.RequiresApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

public class ServerAdapter {
    private SendJsonToServer sendJsonDataToServer;

    public void sendDataToServer(String[] clusters, String uuid) {
        JSONObject post_dict = new JSONObject();

        try {
            post_dict.put("Command" , "ClusterSubmission");
            post_dict.put("Clusters", clusters);
            post_dict.put("UUID", uuid);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (post_dict.length() > 0) {
            sendJsonDataToServer.execute(String.valueOf(post_dict));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String statePoll(String userid) {
        JSONObject state = new JSONObject();

        byte[] data = userid.getBytes(StandardCharsets.UTF_8);
        String encrData = Base64.encodeToString(data, Base64.DEFAULT);

        try {
            state.put("Command" , "StatePoll");
            state.put("UUID", encrData);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (state.length() > 0) {
            // Returns the Answer from the server
            return sendJsonDataToServer.doInBackground(String.valueOf(state));
        }
        else {
            return null;
        }
    }

    public String getPublicKey() {

        JSONObject state = new JSONObject();

        try {
            state.put("Command" , "RequestServerPubKey");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (state.length() > 0) {
            // Returns the Answer from the server (pkey)
            return sendJsonDataToServer.doInBackground(String.valueOf(state));
        }
        else {
            return null;
        }
    }
}
