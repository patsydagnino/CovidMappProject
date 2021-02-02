package net.aptivist.covidmappproject

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.room.Room
import com.google.gson.Gson

import net.aptivist.covidmappproject.helpers.BASEURLGLOBALSTATUS
import net.aptivist.covidmappproject.helpers.BASEURLUSASTATES
import net.aptivist.covidmappproject.data.api.implementations.CasesCountriesServiceImpl
import net.aptivist.covidmappproject.data.api.implementations.CasesGlobalStatusServiceImpl
import net.aptivist.covidmappproject.data.api.implementations.CasesMexicoStatesServiceImpl
import net.aptivist.covidmappproject.data.api.implementations.CasesUSAStatesServiceImpl
import net.aptivist.covidmappproject.data.api.interfaces.ICaseUSAStatesService
import net.aptivist.covidmappproject.data.api.interfaces.ICasesCountriesService
import net.aptivist.covidmappproject.data.api.interfaces.ICasesGlobalStatusService

import net.aptivist.covidmappproject.data.repository.AppDataBase
import net.aptivist.covidmappproject.data.repository.CasesUSAStateRepository
import net.aptivist.covidmappproject.data.repository.dao.ICasesUSAStateDao
import net.aptivist.covidmappproject.helpers.DATABASE_NAME
import net.aptivist.covidmappproject.data.api.interfaces.ICasesMexicoStatesService
import net.aptivist.covidmappproject.helpers.BASEURLMEXICOSTATES
import net.aptivist.covidmappproject.ui.map.viewmodel.MapViewModel
import net.aptivist.covidmappproject.ui.splash.viewmodel.SplashViewModel
import net.aptivist.covidmappproject.ui.tips.viewmodel.TipsViewModel
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(singletonModule,implementationModule, viewModelModule)
        }

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.getDefaultNightMode())
    }

    private val singletonModule= module {

        single() {
            val cacheSize: Long = 10* 1024 *1024 //10mb
            val mCache = Cache(cacheDir,cacheSize)
            OkHttpClient().newBuilder()
                .cache(mCache)
                .addInterceptor(HttpLoggingInterceptor().apply { level = if(BuildConfig.DEBUG)HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE })
                .build()
        }

        single(named("USAStates")) {
            Retrofit.Builder()
                .baseUrl(BASEURLUSASTATES)
                .client(get())
                .addConverterFactory(GsonConverterFactory.create(get()))
                .build()
        }
        single(named("GlobalStatus")) {
            Retrofit.Builder()
                .baseUrl(BASEURLGLOBALSTATUS)
                .client(get())
                .addConverterFactory(GsonConverterFactory.create(get()))
                .build()
        }
        single{ Gson() }
        single { Room.databaseBuilder(applicationContext, AppDataBase::class.java, DATABASE_NAME).build()}
        single { get<AppDataBase>().casesUSAStateDao() }
        single { get<AppDataBase>().casesGlobalStatusDao() }
        single { get<AppDataBase>().casesCountryDao() }
    }
    private val implementationModule= module {
        factory<ICaseUSAStatesService>{ CasesUSAStatesServiceImpl(get(named("USAStates")))}
        factory <ICasesGlobalStatusService>{ CasesGlobalStatusServiceImpl(get(named("GlobalStatus"))) }
        factory <ICasesCountriesService> { CasesCountriesServiceImpl(get(), get(), BASEURLGLOBALSTATUS) }
        factory <ICasesMexicoStatesService> { CasesMexicoStatesServiceImpl(get(), get(), BASEURLMEXICOSTATES) }
    }
    private val viewModelModule= module {
        viewModel { SplashViewModel() }

        viewModel{MapViewModel(get(), get(), get(),get())}

        viewModel{ TipsViewModel() }

    }
    /*private val repositoryModule = module {
        fun provideDao(database: AppDataBase): ICasesUSAStateDao {
            return database.casesUSAStateDao()
        }
        single { provideDao(get()) }
        fun provideCasesStatesUSARepository(interf: ICasesUSAStateDao): CasesUSAStateRepository {
            return CasesUSAStateRepository(interf)
        }
        single { provideCasesStatesUSARepository(get()) }

    }*/
}