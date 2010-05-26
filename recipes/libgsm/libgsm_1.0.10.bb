DESCRIPTION = "GSM Audio Library"
SECTION = "libs"
PRIORITY = "optional"
#DEPENDS = ""
LICENSE = "libgsm"

PR = "r2"

inherit pkgconfig gpe

SRC_URI = "http://www.netsw.org/audio/convert/gsm-${PV}.tar.gz \
           file://libgsm_patch;apply=yes;striplevel=0"

S = "${WORKDIR}/gsm-1.0-pl10/"

headers = "gsm.h"

do_stage () {
        oe_libinstall -a -C lib libgsm ${STAGING_LIBDIR}
        mkdir -p ${STAGING_INCDIR}/gsm
        for h in ${headers}; do
            install -m 0644 ${S}/inc/$h ${STAGING_INCDIR}/gsm/$h
        done
        ln -s ${STAGING_INCDIR}/gsm/gsm.h ${STAGING_INCDIR}/gsm.h
}

#do_install () {
#       gpe_do_install
#       oe_runmake PREFIX=${prefix} DESTDIR=${D} install-devel
#}

SRC_URI[md5sum] = "4b148480f82e96d274248e13880ec873"
SRC_URI[sha256sum] = "ddab700db455e13bcf9bc0592b320e61c6a7c692fb3cef7881b14b2df1a39069"
