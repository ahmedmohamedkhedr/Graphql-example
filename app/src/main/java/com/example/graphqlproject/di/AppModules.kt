package com.example.graphqlproject.di

import android.content.Context
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.cache.normalized.NormalizedCache
import com.apollographql.apollo.cache.normalized.NormalizedCacheFactory
import com.apollographql.apollo.cache.normalized.lru.EvictionPolicy
import com.apollographql.apollo.cache.normalized.lru.LruNormalizedCache
import com.apollographql.apollo.cache.normalized.lru.LruNormalizedCacheFactory
import com.apollographql.apollo.cache.normalized.sql.SqlNormalizedCacheFactory
import com.example.graphqlproject.AddTodoMutation
import com.example.graphqlproject.DataQuery
import com.example.graphqlproject.GetUserPostsQuery
import com.example.graphqlproject.adapters.HomeFragmentAdapter
import com.example.graphqlproject.adapters.ProfileFragmentAdapter
import com.example.graphqlproject.network.AuthorizationInterceptor
import com.example.graphqlproject.presenters.AddPostFragmentPresenterImp
import com.example.graphqlproject.presenters.HomeFragmentPresenterImp
import com.example.graphqlproject.presenters.ProfileFragmentPresenterImp
import com.example.graphqlproject.ui.views.AddPostFragmentView
import com.example.graphqlproject.ui.views.HomeFragmentView
import com.example.graphqlproject.ui.views.ProfileFragmentView
import com.example.graphqlproject.utilities.SERVER_URL
import io.reactivex.disposables.CompositeDisposable
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


fun getSqlCachingFactory(context: Context, name: String): SqlNormalizedCacheFactory {
    return SqlNormalizedCacheFactory(context, name)
}

fun getCachingFactory(sqlCacheFactory: SqlNormalizedCacheFactory): NormalizedCacheFactory<LruNormalizedCache> {
    return LruNormalizedCacheFactory(
        EvictionPolicy.builder().maxSizeBytes(10 * 1024).build()
    ).chain(sqlCacheFactory)
}

fun getClient(
    interceptor: AuthorizationInterceptor,
    factory: NormalizedCacheFactory<NormalizedCache>

): ApolloClient {
    return ApolloClient.builder()
        .serverUrl(SERVER_URL)
        .okHttpClient(
            OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()
        ).normalizedCache(factory)
        .build()
}

val appModules = module {
    //for interceptor
    single {
        AuthorizationInterceptor()
    }
    //for sql normalized caching factory
    single {
        getSqlCachingFactory(androidContext(), "db_name")
    }
    //for normalized caching
    single {
        getCachingFactory(get())
    }

    //for network client
    single {
        getClient(get(), get())
    }

    //for get composite disposable
    factory {
        CompositeDisposable()
    }
    //for get data query
    single {
        DataQuery.builder().build()
    }
    //for get user posts query
    single {
        GetUserPostsQuery.builder().build()
    }
    //for get add new post mutation
    factory { (post: String) ->
        AddTodoMutation.builder().isPublic(true).todo(post).build()
    }
    //for get home fragment presenter
    factory { (view: HomeFragmentView) ->
        HomeFragmentPresenterImp(view, get())
    }
    //for profile fragment presenter
    factory { (view: ProfileFragmentView) ->
        ProfileFragmentPresenterImp(view, get())
    }
    //for add post fragment presenter
    factory { (view: AddPostFragmentView) ->
        AddPostFragmentPresenterImp(view, get())
    }
    //for Home fragment adapter
    factory { (list: MutableList<DataQuery.Todo>) ->
        HomeFragmentAdapter(list)
    }
    // for profile fragment adapter
    factory { (list: MutableList<GetUserPostsQuery.Todo>) ->
        ProfileFragmentAdapter(list)
    }


}