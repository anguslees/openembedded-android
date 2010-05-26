DEPENDS += "cairo sqlite libnotify"

PV = "3.0.1+3.5b4"
MOZPV = "3.5b4"
PR = "r3"

SRC_URI = "http://ftp.mozilla.org/pub/mozilla.org/firefox/nightly/${MOZPV}-candidates/build1/source/firefox-${MOZPV}-source.tar.bz2 \
	file://jsautocfg.h \
	file://jsautocfg-dontoverwrite.patch \
"

S = "${WORKDIR}/mozilla-1.9.1"

DEFAULT_PREFERENCE = "-10"

inherit mozilla
require firefox.inc

FULL_OPTIMIZATION = "-fexpensive-optimizations -fomit-frame-pointer -frename-registers -O2"

do_configure_prepend() {
	sed -i -e s:'head -1':'head -n1':g client.mk
	oe_runmake -f client.mk CONFIGURE_ARGS="${EXTRA_OECONF}" configure
}

do_compile_prepend() {
	cp ${WORKDIR}/jsautocfg.h ${S}/js/src/
        sed -i -e "s|CPU_ARCH =|CPU_ARCH = ${TARGET_ARCH}|" \
               -e  s:'$(OS_TEST)':${TARGET_ARCH}:g \
                   ${S}/security/coreconf/Linux.mk
}

do_stage() {
        install -d ${STAGING_INCDIR}/firefox-${MOZPV}
        cd dist/sdk/include
		rm -rf obsolete
        headers=`find . -name "*.h"`
        for f in $headers
        do
                install -D -m 0644 $f ${STAGING_INCDIR}/firefox-${MOZPV}/
        done
        # removes 2 lines that call absent headers
        sed -e '178,179d' ${STAGING_INCDIR}/firefox-${MOZPV}/nsIServiceManager.h
}


SRC_URI[md5sum] = "ff6bf463300cb56bcf750973b2af3d80"
SRC_URI[sha256sum] = "b653f2fa8dfdd0a54a6b8c576769719cf9014ca6b09f8e9d351721a51766d95d"
