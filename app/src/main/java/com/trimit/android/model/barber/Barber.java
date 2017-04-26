
package com.trimit.android.model.barber;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Barber {

    @SerializedName("address_line_2")
    @Expose
    private String addressLine2;
    @SerializedName("town")
    @Expose
    private String town;
    @SerializedName("county")
    @Expose
    private String county;
    @SerializedName("postcode")
    @Expose
    private String postcode;
    @SerializedName("account_id")
    @Expose
    private Integer accountId;
    @SerializedName("overall_stars")
    @Expose
    private String overallStars;
    @SerializedName("barber_id")
    @Expose
    private Integer barberId;
    @SerializedName("contact_number")
    @Expose
    private String contactNumber;
    @SerializedName("barbershop_id")
    @Expose
    private Integer barbershopId;
    @SerializedName("bio")
    @Expose
    private String bio;
    @SerializedName("account_number")
    @Expose
    private String accountNumber;
    @SerializedName("sortcode")
    @Expose
    private String sortcode;
    @SerializedName("address_line_1")
    @Expose
    private String addressLine1;
    @SerializedName("cash_only")
    @Expose
    private Boolean cashOnly;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("inactive")
    @Expose
    private Boolean inactive;
    @SerializedName("holding")
    @Expose
    private Boolean holding;
    @SerializedName("account")
    @Expose
    private List<Account> account = null;
    @SerializedName("barbershop")
    @Expose
    private List<Barbershop> barbershop = null;
    @SerializedName("photo")
    @Expose
    private Photo photo;
    @SerializedName("barber_type")
    @Expose
    private List<BarberType> barberType = null;
    @SerializedName("available")
    @Expose
    private Boolean available;
    @SerializedName("offered_service")
    @Expose
    private List<OfferedService> offeredService = null;

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getOverallStars() {
        return overallStars;
    }

    public void setOverallStars(String overallStars) {
        this.overallStars = overallStars;
    }

    public Integer getBarberId() {
        return barberId;
    }

    public void setBarberId(Integer barberId) {
        this.barberId = barberId;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public Integer getBarbershopId() {
        return barbershopId;
    }

    public void setBarbershopId(Integer barbershopId) {
        this.barbershopId = barbershopId;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getSortcode() {
        return sortcode;
    }

    public void setSortcode(String sortcode) {
        this.sortcode = sortcode;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public Boolean getCashOnly() {
        return cashOnly;
    }

    public void setCashOnly(Boolean cashOnly) {
        this.cashOnly = cashOnly;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Boolean getInactive() {
        return inactive;
    }

    public void setInactive(Boolean inactive) {
        this.inactive = inactive;
    }

    public Boolean getHolding() {
        return holding;
    }

    public void setHolding(Boolean holding) {
        this.holding = holding;
    }

    public List<Account> getAccount() {
        return account;
    }

    public void setAccount(List<Account> account) {
        this.account = account;
    }

    public List<Barbershop> getBarbershop() {
        return barbershop;
    }

    public void setBarbershop(List<Barbershop> barbershop) {
        this.barbershop = barbershop;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public List<BarberType> getBarberType() {
        return barberType;
    }

    public void setBarberType(List<BarberType> barberType) {
        this.barberType = barberType;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public List<OfferedService> getOfferedService() {
        return offeredService;
    }

    public void setOfferedService(List<OfferedService> offeredService) {
        this.offeredService = offeredService;
    }

    @Override
    public String toString() {
        return "Barber{" +
                "addressLine2='" + addressLine2 + '\'' +
                ", town='" + town + '\'' +
                ", county='" + county + '\'' +
                ", postcode='" + postcode + '\'' +
                ", accountId=" + accountId +
                ", overallStars='" + overallStars + '\'' +
                ", barberId=" + barberId +
                ", contactNumber='" + contactNumber + '\'' +
                ", barbershopId=" + barbershopId +
                ", bio='" + bio + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", sortcode='" + sortcode + '\'' +
                ", addressLine1='" + addressLine1 + '\'' +
                ", cashOnly=" + cashOnly +
                ", gender='" + gender + '\'' +
                ", inactive=" + inactive +
                ", holding=" + holding +
                ", account=" + account +
                ", barbershop=" + barbershop +
                ", photo=" + photo +
                ", barberType=" + barberType +
                ", available=" + available +
                ", offeredService=" + offeredService +
                '}';
    }
}
