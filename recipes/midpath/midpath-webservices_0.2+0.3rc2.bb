require midpath-common.inc

PR = "r0"

SRC_URI = "${SOURCEFORGE_MIRROR}/midpath/midpath-0.3rc2.tar.gz"

S = "${WORKDIR}/midpath-0.3rc2"

DEPENDS += "midpath-core"
RSUGGESTS_${PN} = "${PN}-jaxp"

DESCRIPTION = "Implementation of the JSR172 J2ME Web Services API for use in the MIDPath library"

JAR = "jsr172-jaxp.jar"
JAR2 = "jsr172-jaxrpc.jar"

do_compile() {
  # Only web services API is enabled.
  midpath_build \
    --disable-midpath \
    --disable-cldc \
    --disable-sdljava-cldc \
    --disable-escher-cldc \
    --disable-jlayerme-cldc \
    --disable-jorbis-cldc \
    --disable-avetanabt-cldc \
    --disable-jgl-cldc \
    --disable-location-api \
    --disable-messaging-api \
    --disable-svg-api \
    --disable-opengl-api \
    --disable-m3g-api \
    --disable-demos
}

do_install() {
	install -d ${D}${datadir}/midpath
	install -m 0644 dist/${JAR} ${D}${datadir}/midpath
	install -m 0644 dist/${JAR2} ${D}${datadir}/midpath
}

do_stage() {
	install -d ${STAGING_DATADIR}/midpath
	install -m 0644 dist/${JAR} ${STAGING_DATADIR}/midpath
	install -m 0644 dist/${JAR2} ${STAGING_DATADIR}/midpath
}
	
PACKAGES = "${PN} ${PN}-jaxp"

FILES_${PN}-jaxp  = "${datadir}/midpath/${JAR}"
FILES_${PN}  = "${datadir}/midpath/${JAR2}"

SRC_URI[md5sum] = "d03cd88f51f82bbcfcfa5b65df0da5b0"
SRC_URI[sha256sum] = "e235ca7470e7cdfb90e3806fbcc1b2c450db286276136a2523c7ae26a804a100"
