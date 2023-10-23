package com.example.cbleecher;

import org.json.JSONObject;
import kotlin.jvm.internal.Intrinsics;



public final class JsonFormater {
    private final String str;

    public JsonFormater(String str) {
        Intrinsics.checkNotNullParameter(str, "str");
        this.str = str;
    }

    public final String run() {
        try {
            String jSONObject = new JSONObject(this.str).toString(4);
            Intrinsics.checkNotNullExpressionValue(jSONObject, "{\n            JSONObject…tr).toString(4)\n        }");
            String cleandeStr = jSONObject.replace("\\","");
            return cleandeStr;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
