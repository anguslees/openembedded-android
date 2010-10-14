DESCRIPTION = "OpenAL is a cross-platform 3D audio API."
PN = "openal"
SRCREV = ea44b95252ce15dd38fb7563477e9e35b1c147dc

SRC_URI = "git://repo.or.cz/openal-soft/android.git;protocol=git \
	file://android-backend.patch"

S = "${WORKDIR}/git"

inherit cmake pkgconfig

OECMAKE_BUILDPATH = "build"
OECMAKE_SOURCEPATH = "../"

EXTRA_OECMAKE += "-DWAVE=OFF -DEXAMPLES=OFF -DLIBTYPE=STATIC"

# Do this to match openal_*.bb, but I think it is wrong/insufficient.
# Particularly with -DLIBTYPE=STATIC, the libopenal package is empty anyway.
PACKAGES =+ "libopenal"

FILES_libopenal += "${libdir}/libopenal.so.*"
FILES_openal-dev += "${bindir}/openal-config"
FILES_openal = ""
