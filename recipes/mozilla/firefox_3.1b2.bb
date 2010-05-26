DEPENDS += "cairo"

PV = "3.0.1+3.1b2"
MOZPV = "3.1b2"
PR = "r1"

SRC_URI = "http://ftp.mozilla.org/pub/mozilla.org/firefox/nightly/${MOZPV}-candidates/build2/source/firefox-${MOZPV}-source.tar.bz2 \
	file://jsautocfg.h \
	file://jsautocfg-dontoverwrite.patch \
"

S = "${WORKDIR}/mozilla-central"

DEFAULT_PREFERENCE = "-1"

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


SRC_URI[md5sum] = "c315e06cdfe9ee76aa4831ece1ed5df3"
SRC_URI[sha256sum] = "402ca3641f1724f5a4ec1ac3e283cd1334bd85c4b5de7744bd267454ef31259e"
