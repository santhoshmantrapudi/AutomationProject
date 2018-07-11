package com.Selenium.JsonResponse;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateResponse {

@SerializedName("response")
@Expose
private List<response> response = null;

public List<response> getResponse() {
return response;
}

public void setResponse(List<response> response) {
this.response = response;
}

}