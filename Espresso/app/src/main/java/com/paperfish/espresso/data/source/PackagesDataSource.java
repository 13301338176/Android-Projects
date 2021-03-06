/*
 *  Copyright(c) 2017 lizhaotailang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.paperfish.espresso.data.source;

import android.support.annotation.NonNull;

import com.paperfish.espresso.data.Package;

import java.util.List;

import io.reactivex.Observable;

/**
 * 接口：用于获取包裹数据源，并包含了一些对包裹数据的操作
 */

public interface PackagesDataSource {

    Observable<List<Package>> getPackages();

    Observable<Package> getPackage(@NonNull final String packNumber);

    void savePackage(@NonNull Package pack);

    void deletePackage(@NonNull String packageId);

    Observable<List<Package>> refreshPackages();

    Observable<Package> refreshPackage(@NonNull String packageId);

    void setAllPackagesRead();

    void setPackageReadable(@NonNull String packageId, boolean readable);

    boolean isPackageExist(@NonNull String packageId);

    void updatePackageName(@NonNull String packageId, @NonNull String name);

    Observable<List<Package>> searchPackages(@NonNull String keyWords);

}
