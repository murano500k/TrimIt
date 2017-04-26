
package com.trimit.android.model.barber;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Photo {

    @SerializedName("profile_picture")
    @Expose
    private ProfilePicture profilePicture;
    @SerializedName("cover_picture")
    @Expose
    private CoverPicture coverPicture;
    @SerializedName("gallery")
    @Expose
    private List<Gallery> gallery = null;

    public ProfilePicture getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(ProfilePicture profilePicture) {
        this.profilePicture = profilePicture;
    }

    public CoverPicture getCoverPicture() {
        return coverPicture;
    }

    public void setCoverPicture(CoverPicture coverPicture) {
        this.coverPicture = coverPicture;
    }

    public List<Gallery> getGallery() {
        return gallery;
    }

    public void setGallery(List<Gallery> gallery) {
        this.gallery = gallery;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "profilePicture=" + profilePicture +
                ", coverPicture=" + coverPicture +
                ", gallery=" + gallery +
                '}';
    }
}
