/*
 * Copyright (C) 2014 barter.li
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and
  * limitations under the License.
 */

package singularity.walkineasy.utils;

import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.text.TextUtils;

import java.util.Locale;

import singularity.walkineasy.http.HttpConstants;


/**
 * Class that holds the App Constants
 *
 * @author Vinay S Shenoy
 */
public class AppConstants {


    /**
     * Singleton to hold frequently accessed info in memory
     *
     * @author Vinay S Shenoy
     */
    public enum UserInfo {

        INSTANCE;

        private String mAuthToken;
        private String mEmail;
        private String mId;
        private String mProfilePicture;
        private String mAuthHeader;
        private String mDeviceId;
        private String mFirstName;
        private String mLastName;

        private UserInfo() {
            reset();
        }

        public void reset() {
            mAuthToken = "";
            mAuthHeader = "";
            mEmail = "";
            mId = "";
            mProfilePicture = "";
            mFirstName = "";
            mLastName = "";
        }

        public String getAuthToken() {
            return mAuthToken;
        }

        public void setAuthToken(final String authToken) {

            if (authToken == null) {
                mAuthToken = "";
            } else {
                mAuthToken = authToken;
            }
        }

        public String getEmail() {
            return mEmail;
        }

        public void setEmail(final String email) {

            if (email == null) {
                mEmail = "";
            } else {
                mEmail = email;
            }
        }

        public String getId() {
            return mId;
        }

        public void setId(final String id) {

            if (id == null) {
                mId = "";
            } else {
                mId = id;
            }
        }

        public String getProfilePicture() {
            return mProfilePicture;
        }

        public void setProfilePicture(final String profilePicture) {

            if (profilePicture == null) {
                mProfilePicture = "";
            } else {
                mProfilePicture = profilePicture;
            }

        }

        public String getDeviceId() {
            return mDeviceId;
        }

        public void setDeviceId(final String deviceId) {
            mDeviceId = deviceId;
        }

        public String getAuthHeader() {

            if (TextUtils.isEmpty(mAuthHeader)
                    && !TextUtils.isEmpty(mAuthToken)
                    && !TextUtils.isEmpty(mEmail)) {
                mAuthHeader = String
                        .format(Locale.US, HttpConstants.HEADER_AUTHORIZATION_FORMAT, mAuthToken,
                                mEmail);
            }
            return mAuthHeader;
        }

        public String getFirstName() {
            return mFirstName;
        }

        public void setFirstName(final String firstName) {

            mFirstName = firstName;
        }

        public String getLastName() {
            return mLastName;
        }

        public void setLastName(final String lastName) {

            mLastName = lastName;
        }

    }

    /**
     * Singleton to hold the current network state. Broadcast receiver for network state will be
     * used to keep this updated
     *
     * @author Vinay S Shenoy
     */
    public enum DeviceInfo {

        INSTANCE;

        private final Location defaultLocation = new Location(LocationManager.PASSIVE_PROVIDER);

        private boolean mIsNetworkConnected;
        private int mCurrentNetworkType;
        private Location mLatestLocation;

        private DeviceInfo() {
            reset();
        }

        public void reset() {

            mIsNetworkConnected = false;
            mCurrentNetworkType = ConnectivityManager.TYPE_DUMMY;
            mLatestLocation = defaultLocation;
        }

        public boolean isNetworkConnected() {
            return mIsNetworkConnected;
        }

        public void setNetworkConnected(final boolean isNetworkConnected) {
            mIsNetworkConnected = isNetworkConnected;
        }

        public int getCurrentNetworkType() {
            return mCurrentNetworkType;
        }

        public void setCurrentNetworkType(final int currentNetworkType) {
            mCurrentNetworkType = currentNetworkType;
        }

        public Location getLatestLocation() {
            return mLatestLocation;
        }

        public void setLatestLocation(final Location latestLocation) {
            if (latestLocation == null) {
                mLatestLocation = defaultLocation;
            }
            mLatestLocation = latestLocation;
        }

    }

    public static interface RequestCodes {
        public static final int SCAN_ISBN = 100;

    }


    public static interface ResultCodes {

        public static final int FAILURE = -1;
        public static final int CANCEL = 0;
        public static final int SUCCESS = 1;
    }

    /**
     * Constant Interface, DO NOT IMPLEMENT
     *
     * @author vinaysshenoy
     */
    public static interface Keys {

        public static final String ISBN = "isbn";
        public static final String BOOK_TITLE = "book_title";
        public static final String AUTHOR = "author";
        public static final String HOUSE_TYPE = "house_type";

        public static final String ID = "id";


    }


    public static interface FragmentTags {
        public static final String MAIN_FRAGMENT = "main_fragment";
        public static final String SECOND_FRAGMENT = "second_fragment";
    }

    public static interface BarterType {
        public static final String BARTER = "barter";
        public static final String SALE = "sale";
        public static final String LEND = "lend";
    }

    public static interface Loaders {
        public static final int SEARCH_BOOKS = 201;
    }


    public static interface QueryTokens {

        // 1-100 for load queries
        public static final int LOAD_LOCATION_FROM_PROFILE_EDIT_PAGE = 1;

        // 101-200 for insert queries

        // 201-300 for update queries
        public static final int UPDATE_MESSAGE_STATUS = 201;

        //301-400 for delete queries
        public static final int DELETE_BOOKS_SEARCH_RESULTS = 301;

    }
}
