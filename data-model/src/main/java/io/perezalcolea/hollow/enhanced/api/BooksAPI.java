package io.perezalcolea.hollow.enhanced.api;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.Map;
import com.netflix.hollow.api.consumer.HollowConsumerAPI;
import com.netflix.hollow.api.custom.HollowAPI;
import com.netflix.hollow.core.read.dataaccess.HollowDataAccess;
import com.netflix.hollow.core.read.dataaccess.HollowTypeDataAccess;
import com.netflix.hollow.core.read.dataaccess.HollowObjectTypeDataAccess;
import com.netflix.hollow.core.read.dataaccess.HollowListTypeDataAccess;
import com.netflix.hollow.core.read.dataaccess.HollowSetTypeDataAccess;
import com.netflix.hollow.core.read.dataaccess.HollowMapTypeDataAccess;
import com.netflix.hollow.core.read.dataaccess.missing.HollowObjectMissingDataAccess;
import com.netflix.hollow.core.read.dataaccess.missing.HollowListMissingDataAccess;
import com.netflix.hollow.core.read.dataaccess.missing.HollowSetMissingDataAccess;
import com.netflix.hollow.core.read.dataaccess.missing.HollowMapMissingDataAccess;
import com.netflix.hollow.api.objects.provider.HollowFactory;
import com.netflix.hollow.api.objects.provider.HollowObjectProvider;
import com.netflix.hollow.api.objects.provider.HollowObjectCacheProvider;
import com.netflix.hollow.api.objects.provider.HollowObjectFactoryProvider;
import com.netflix.hollow.api.sampling.HollowObjectCreationSampler;
import com.netflix.hollow.api.sampling.HollowSamplingDirector;
import com.netflix.hollow.api.sampling.SampleResult;
import com.netflix.hollow.core.util.AllHollowRecordCollection;

@SuppressWarnings("all")
public class BooksAPI extends HollowAPI  {

    private final HollowObjectCreationSampler objectCreationSampler;

    private final AuthorTypeAPI authorTypeAPI;
    private final LanguageTypeAPI languageTypeAPI;
    private final PublisherTypeAPI publisherTypeAPI;
    private final SetOfAuthorTypeAPI setOfAuthorTypeAPI;
    private final BookTypeAPI bookTypeAPI;

    private final HollowObjectProvider authorProvider;
    private final HollowObjectProvider languageProvider;
    private final HollowObjectProvider publisherProvider;
    private final HollowObjectProvider setOfAuthorProvider;
    private final HollowObjectProvider bookProvider;

    public BooksAPI(HollowDataAccess dataAccess) {
        this(dataAccess, Collections.<String>emptySet());
    }

    public BooksAPI(HollowDataAccess dataAccess, Set<String> cachedTypes) {
        this(dataAccess, cachedTypes, Collections.<String, HollowFactory<?>>emptyMap());
    }

    public BooksAPI(HollowDataAccess dataAccess, Set<String> cachedTypes, Map<String, HollowFactory<?>> factoryOverrides) {
        this(dataAccess, cachedTypes, factoryOverrides, null);
    }

    public BooksAPI(HollowDataAccess dataAccess, Set<String> cachedTypes, Map<String, HollowFactory<?>> factoryOverrides, BooksAPI previousCycleAPI) {
        super(dataAccess);
        HollowTypeDataAccess typeDataAccess;
        HollowFactory factory;

        objectCreationSampler = new HollowObjectCreationSampler("Author","Language","Publisher","SetOfAuthor","Book");

        typeDataAccess = dataAccess.getTypeDataAccess("Author");
        if(typeDataAccess != null) {
            authorTypeAPI = new AuthorTypeAPI(this, (HollowObjectTypeDataAccess)typeDataAccess);
        } else {
            authorTypeAPI = new AuthorTypeAPI(this, new HollowObjectMissingDataAccess(dataAccess, "Author"));
        }
        addTypeAPI(authorTypeAPI);
        factory = factoryOverrides.get("Author");
        if(factory == null)
            factory = new AuthorHollowFactory();
        if(cachedTypes.contains("Author")) {
            HollowObjectCacheProvider previousCacheProvider = null;
            if(previousCycleAPI != null && (previousCycleAPI.authorProvider instanceof HollowObjectCacheProvider))
                previousCacheProvider = (HollowObjectCacheProvider) previousCycleAPI.authorProvider;
            authorProvider = new HollowObjectCacheProvider(typeDataAccess, authorTypeAPI, factory, previousCacheProvider);
        } else {
            authorProvider = new HollowObjectFactoryProvider(typeDataAccess, authorTypeAPI, factory);
        }

        typeDataAccess = dataAccess.getTypeDataAccess("Language");
        if(typeDataAccess != null) {
            languageTypeAPI = new LanguageTypeAPI(this, (HollowObjectTypeDataAccess)typeDataAccess);
        } else {
            languageTypeAPI = new LanguageTypeAPI(this, new HollowObjectMissingDataAccess(dataAccess, "Language"));
        }
        addTypeAPI(languageTypeAPI);
        factory = factoryOverrides.get("Language");
        if(factory == null)
            factory = new LanguageHollowFactory();
        if(cachedTypes.contains("Language")) {
            HollowObjectCacheProvider previousCacheProvider = null;
            if(previousCycleAPI != null && (previousCycleAPI.languageProvider instanceof HollowObjectCacheProvider))
                previousCacheProvider = (HollowObjectCacheProvider) previousCycleAPI.languageProvider;
            languageProvider = new HollowObjectCacheProvider(typeDataAccess, languageTypeAPI, factory, previousCacheProvider);
        } else {
            languageProvider = new HollowObjectFactoryProvider(typeDataAccess, languageTypeAPI, factory);
        }

        typeDataAccess = dataAccess.getTypeDataAccess("Publisher");
        if(typeDataAccess != null) {
            publisherTypeAPI = new PublisherTypeAPI(this, (HollowObjectTypeDataAccess)typeDataAccess);
        } else {
            publisherTypeAPI = new PublisherTypeAPI(this, new HollowObjectMissingDataAccess(dataAccess, "Publisher"));
        }
        addTypeAPI(publisherTypeAPI);
        factory = factoryOverrides.get("Publisher");
        if(factory == null)
            factory = new PublisherHollowFactory();
        if(cachedTypes.contains("Publisher")) {
            HollowObjectCacheProvider previousCacheProvider = null;
            if(previousCycleAPI != null && (previousCycleAPI.publisherProvider instanceof HollowObjectCacheProvider))
                previousCacheProvider = (HollowObjectCacheProvider) previousCycleAPI.publisherProvider;
            publisherProvider = new HollowObjectCacheProvider(typeDataAccess, publisherTypeAPI, factory, previousCacheProvider);
        } else {
            publisherProvider = new HollowObjectFactoryProvider(typeDataAccess, publisherTypeAPI, factory);
        }

        typeDataAccess = dataAccess.getTypeDataAccess("SetOfAuthor");
        if(typeDataAccess != null) {
            setOfAuthorTypeAPI = new SetOfAuthorTypeAPI(this, (HollowSetTypeDataAccess)typeDataAccess);
        } else {
            setOfAuthorTypeAPI = new SetOfAuthorTypeAPI(this, new HollowSetMissingDataAccess(dataAccess, "SetOfAuthor"));
        }
        addTypeAPI(setOfAuthorTypeAPI);
        factory = factoryOverrides.get("SetOfAuthor");
        if(factory == null)
            factory = new SetOfAuthorHollowFactory();
        if(cachedTypes.contains("SetOfAuthor")) {
            HollowObjectCacheProvider previousCacheProvider = null;
            if(previousCycleAPI != null && (previousCycleAPI.setOfAuthorProvider instanceof HollowObjectCacheProvider))
                previousCacheProvider = (HollowObjectCacheProvider) previousCycleAPI.setOfAuthorProvider;
            setOfAuthorProvider = new HollowObjectCacheProvider(typeDataAccess, setOfAuthorTypeAPI, factory, previousCacheProvider);
        } else {
            setOfAuthorProvider = new HollowObjectFactoryProvider(typeDataAccess, setOfAuthorTypeAPI, factory);
        }

        typeDataAccess = dataAccess.getTypeDataAccess("Book");
        if(typeDataAccess != null) {
            bookTypeAPI = new BookTypeAPI(this, (HollowObjectTypeDataAccess)typeDataAccess);
        } else {
            bookTypeAPI = new BookTypeAPI(this, new HollowObjectMissingDataAccess(dataAccess, "Book"));
        }
        addTypeAPI(bookTypeAPI);
        factory = factoryOverrides.get("Book");
        if(factory == null)
            factory = new BookHollowFactory();
        if(cachedTypes.contains("Book")) {
            HollowObjectCacheProvider previousCacheProvider = null;
            if(previousCycleAPI != null && (previousCycleAPI.bookProvider instanceof HollowObjectCacheProvider))
                previousCacheProvider = (HollowObjectCacheProvider) previousCycleAPI.bookProvider;
            bookProvider = new HollowObjectCacheProvider(typeDataAccess, bookTypeAPI, factory, previousCacheProvider);
        } else {
            bookProvider = new HollowObjectFactoryProvider(typeDataAccess, bookTypeAPI, factory);
        }

    }

    public void detachCaches() {
        if(authorProvider instanceof HollowObjectCacheProvider)
            ((HollowObjectCacheProvider)authorProvider).detach();
        if(languageProvider instanceof HollowObjectCacheProvider)
            ((HollowObjectCacheProvider)languageProvider).detach();
        if(publisherProvider instanceof HollowObjectCacheProvider)
            ((HollowObjectCacheProvider)publisherProvider).detach();
        if(setOfAuthorProvider instanceof HollowObjectCacheProvider)
            ((HollowObjectCacheProvider)setOfAuthorProvider).detach();
        if(bookProvider instanceof HollowObjectCacheProvider)
            ((HollowObjectCacheProvider)bookProvider).detach();
    }

    public AuthorTypeAPI getAuthorTypeAPI() {
        return authorTypeAPI;
    }
    public LanguageTypeAPI getLanguageTypeAPI() {
        return languageTypeAPI;
    }
    public PublisherTypeAPI getPublisherTypeAPI() {
        return publisherTypeAPI;
    }
    public SetOfAuthorTypeAPI getSetOfAuthorTypeAPI() {
        return setOfAuthorTypeAPI;
    }
    public BookTypeAPI getBookTypeAPI() {
        return bookTypeAPI;
    }
    public Collection<Author> getAllAuthor() {
        return new AllHollowRecordCollection<Author>(getDataAccess().getTypeDataAccess("Author").getTypeState()) {
            protected Author getForOrdinal(int ordinal) {
                return getAuthor(ordinal);
            }
        };
    }
    public Author getAuthor(int ordinal) {
        objectCreationSampler.recordCreation(0);
        return (Author)authorProvider.getHollowObject(ordinal);
    }
    public Collection<Language> getAllLanguage() {
        return new AllHollowRecordCollection<Language>(getDataAccess().getTypeDataAccess("Language").getTypeState()) {
            protected Language getForOrdinal(int ordinal) {
                return getLanguage(ordinal);
            }
        };
    }
    public Language getLanguage(int ordinal) {
        objectCreationSampler.recordCreation(1);
        return (Language)languageProvider.getHollowObject(ordinal);
    }
    public Collection<Publisher> getAllPublisher() {
        return new AllHollowRecordCollection<Publisher>(getDataAccess().getTypeDataAccess("Publisher").getTypeState()) {
            protected Publisher getForOrdinal(int ordinal) {
                return getPublisher(ordinal);
            }
        };
    }
    public Publisher getPublisher(int ordinal) {
        objectCreationSampler.recordCreation(2);
        return (Publisher)publisherProvider.getHollowObject(ordinal);
    }
    public Collection<SetOfAuthor> getAllSetOfAuthor() {
        return new AllHollowRecordCollection<SetOfAuthor>(getDataAccess().getTypeDataAccess("SetOfAuthor").getTypeState()) {
            protected SetOfAuthor getForOrdinal(int ordinal) {
                return getSetOfAuthor(ordinal);
            }
        };
    }
    public SetOfAuthor getSetOfAuthor(int ordinal) {
        objectCreationSampler.recordCreation(3);
        return (SetOfAuthor)setOfAuthorProvider.getHollowObject(ordinal);
    }
    public Collection<Book> getAllBook() {
        return new AllHollowRecordCollection<Book>(getDataAccess().getTypeDataAccess("Book").getTypeState()) {
            protected Book getForOrdinal(int ordinal) {
                return getBook(ordinal);
            }
        };
    }
    public Book getBook(int ordinal) {
        objectCreationSampler.recordCreation(4);
        return (Book)bookProvider.getHollowObject(ordinal);
    }
    public void setSamplingDirector(HollowSamplingDirector director) {
        super.setSamplingDirector(director);
        objectCreationSampler.setSamplingDirector(director);
    }

    public Collection<SampleResult> getObjectCreationSamplingResults() {
        return objectCreationSampler.getSampleResults();
    }

}
