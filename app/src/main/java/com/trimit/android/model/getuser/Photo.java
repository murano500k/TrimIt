
package com.trimit.android.model.getuser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Photo {

    @SerializedName("profile_picture")
    @Expose
    private ProfilePicture profilePicture;

    public ProfilePicture getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(ProfilePicture profilePicture) {
        this.profilePicture = profilePicture;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "profilePicture=" + profilePicture +
                '}';
    }
}
