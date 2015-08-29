package singularity.walkineasy.http.models;

import com.google.gson.annotations.SerializedName;


/**
 * Created by sharathkumar on 18/04/15.
 */
public class MyAdvisorsResponseModel {

    //@SerializedName("")
    //public List<Advisor> advisors;

    //public class Advisor {

        /**
         * Full name of the Advisor
         */
        @SerializedName("FullName")
        public String mAdvisorFullName;

        /**
         * Email Id of the Advisor
         */
        @SerializedName("FtatorLoginEmail")
        public String mAdvisorEmail;

        /**
         * Company name of the Advisor.
         */
        @SerializedName("CompanyName")
        public String mAdvisorCompanyName;

        /**
         * Profile picture of the Advisor.
         */
        @SerializedName("ProfilePicturePath")
        public String mAdvisorProfilePicture;

        /**
         * Under what category this associate falls?
         */
        @SerializedName("TargetUserTypeCode")
        public String mAdvisorType;


        @Override
        public String toString() {
            return "Advisor{" +
                    "AdvisorFullName='" + mAdvisorFullName + '\'' +
                    ", AdvisorEmail='" + mAdvisorEmail + '\'' +
                    ", AdvisorCompanyName='" + mAdvisorCompanyName + '\'' +
                    ", AdvisorProfilePicture='" + mAdvisorProfilePicture + '\'' +
                    ", AdvisorType='" + mAdvisorType + '\'' +
                    '}';
        }
    //}
}
