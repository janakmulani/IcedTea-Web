package net.adoptopenjdk.icedteaweb.resources.downloader;

import net.adoptopenjdk.icedteaweb.jnlp.version.VersionId;
import net.adoptopenjdk.icedteaweb.resources.cache.Cache;
import net.sourceforge.jnlp.cache.Resource;

import java.net.URL;
import java.util.List;

/**
 * ...
 */
class ExactVersionedResourceDownloader extends BaseResourceDownloader {

    private final VersionId versionId;

    ExactVersionedResourceDownloader(Resource resource, List<URL> downloadUrls) {
        super(resource, downloadUrls);
        versionId = VersionId.fromString(resource.getRequestVersion().toString());
    }

    @Override
    protected VersionId getVersion(URL downloadFrom, String versionHeaderValue) {
        if (versionHeaderValue != null && versionId.equals(VersionId.fromString(versionHeaderValue))) {
            return versionId;
        }

        if (downloadFrom.toString().contains(versionId.toString())) {
            return versionId;
        }

        throw new IllegalStateException("could not determine the version-id from the response of " + downloadFrom + " -> " + versionHeaderValue);
    }

    @Override
    protected boolean isUpToDate(URL resourceHref, VersionId version, long lastModified) {
        return Cache.isCached(resourceHref, version);
    }
}