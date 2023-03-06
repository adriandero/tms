package at.snt.tms.rest.services.tender;

import java.util.Arrays;
import java.util.Date;

/**
 * Class {@code FilterConfiguration.java}
 * <p>
 * Class representing the FilterConfiguration of the frontend.
 *
 * @author Dominik Fluch
 */
public class FilterConfiguration {
    private String[] platforms;
    private String[] companies;
    private String[] titles;
    private String[] intStatus;
    private String[] extStatus;
    private String[] files;
    private String[] uptDetails;
    private String[] users;
    private Date startDate;
    private Date endDate;
    private SortMode sortBy;

    protected FilterConfiguration() {}

    @Override
    public String toString() {
        return "FilterConfiguration{" +
                "platforms=" + Arrays.toString(platforms) +
                ", companies=" + Arrays.toString(companies) +
                ", titles=" + Arrays.toString(titles) +
                ", intStatus=" + Arrays.toString(intStatus) +
                ", extStatus=" + Arrays.toString(extStatus) +
                ", files=" + Arrays.toString(files) +
                ", uptDetails=" + Arrays.toString(uptDetails) +
                ", users=" + Arrays.toString(users) +
                ", sort=" + sortBy +
                '}';
    }

    public String[] getPlatforms() {
        return platforms;
    }

    public void setPlatforms(String[] platforms) {
        this.platforms = platforms;
    }

    public String[] getCompanies() {
        return companies;
    }

    public void setCompanies(String[] companies) {
        this.companies = companies;
    }

    public String[] getTitles() {
        return titles;
    }

    public void setTitles(String[] titles) {
        this.titles = titles;
    }

    public String[] getIntStatus() {
        return intStatus;
    }

    public void setIntStatus(String[] intStatus) {
        this.intStatus = intStatus;
    }

    public String[] getExtStatus() {
        return extStatus;
    }

    public void setExtStatus(String[] extStatus) {
        this.extStatus = extStatus;
    }

    public String[] getFiles() {
        return files;
    }

    public void setFiles(String[] files) {
        this.files = files;
    }

    public String[] getUptDetails() {
        return uptDetails;
    }

    public void setUptDetails(String[] uptDetails) {
        this.uptDetails = uptDetails;
    }

    public String[] getUsers() {
        return users;
    }

    public void setUsers(String[] users) {
        this.users = users;
    }

    public SortMode getSortBy() {
        return sortBy;
    }

    public void setSortBy(SortMode sortBy) {
        this.sortBy = sortBy;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
