package com.example.administrator.garphdemo

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.apollographql.apollo.rx2.Rx2Apollo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import android.support.v7.widget.LinearLayoutManager





class MainActivity : AppCompatActivity() {

    val TAG: String = "Main activity"
    var etUsername:EditText?=null
    var btSubmit:Button?=null
    var rvRepository: RecyclerView?=null


    val application: App by lazy {
        getApplication() as App
    }

    lateinit var githubRepositoriesCall: ApolloCall<LoadGithubRepositoriesQuery.Data>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        etUsername = findViewById(R.id.editText)
        btSubmit = findViewById(R.id.button)
        rvRepository = findViewById(R.id.rvRepo)
        btSubmit?.setOnClickListener(){
            fetchRepo(etUsername?.text.toString())
        }

        /*    val findQuery: FindQuery = FindQuery.builder()
                    .owner("vinay4791")
                    .name("AndroidArchitectureDemo")
                    .build()



            findQuerycall = application.apolloClient()
                    .query(findQuery)
                    .responseFetcher(ApolloResponseFetchers.NETWORK_FIRST);

            Rx2Apollo.from(findQuerycall)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableObserver<Response<FindQuery.Data>>() {
                        override fun onComplete() {
                        }

                        override fun onNext(t: Response<FindQuery.Data>?) {
                            Log.d(TAG, "success" + t?.data()?.repository)
                        }

                        override fun onError(e: Throwable?) {
                            Log.d(TAG, "error"+e)
                        }

                    })
    */


    }

    @SuppressLint("CheckResult")
    private fun fetchRepo(username:String) {

        val loadGithubMembersQuery: LoadGithubRepositoriesQuery = LoadGithubRepositoriesQuery.builder()
                .login(username)
                .build()

        githubRepositoriesCall = application.apolloClient()
                .query(loadGithubMembersQuery)
                .responseFetcher(ApolloResponseFetchers.NETWORK_FIRST);

        Rx2Apollo.from(githubRepositoriesCall)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver <Response<LoadGithubRepositoriesQuery.Data>>() {
                    override fun onComplete() {
                    }

                    override fun onNext(t: Response<LoadGithubRepositoriesQuery.Data>?) {

                        runOnUiThread {
                           var repoList: MutableList<LoadGithubRepositoriesQuery.Node>? = t?.data()?.user()?.repositories?.nodes

                            rvRepository?.setAdapter(Adapter(repoList))
                            rvRepository?.setLayoutManager(LinearLayoutManager(this@MainActivity))
                        }
                        Log.d(TAG, "success" + t?.data())
                    }

                    override fun onError(e: Throwable?) {
                        Log.d(TAG, "error"+e)
                    }

                })
    }
}
