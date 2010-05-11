SECTION = "libs"
DEPENDS = "jpeg virtual/libusb0 libexif"
DESCRIPTION = "libgphoto2 allows you to access digital cameras"

SRC_URI = "${SOURCEFORGE_MIRROR}/gphoto/libgphoto2-${PV}.tar.bz2"

EXTRA_OECONF_mnci = "--with-drivers=canon --without-serial"
LICENSE = "GPL"
inherit autotools pkgconfig

do_stage() {
	install -d ${STAGING_LIBDIR}
	mv libgphoto2/.libs/libgphoto2.so.2.0.3T libgphoto2/.libs/libgphoto2.so.2.0.3 || true
	oe_libinstall -so -C libgphoto2 libgphoto2 ${STAGING_LIBDIR}
	oe_libinstall -so -C libgphoto2_port/libgphoto2_port libgphoto2_port ${STAGING_LIBDIR}

	install -d ${STAGING_LIBDIR}/gphoto2/2.0
	oe_libinstall -so -C camlibs/canon libgphoto2_canon.so ${STAGING_LIBDIR}/gphoto2/2.0

        install -d ${STAGING_INCDIR}/gphoto2
        for X in gphoto2-abilities-list.h gphoto2-camera.h gphoto2-context.h gphoto2-file.h gphoto2-filesys.h gphoto2.h gphoto2-library.h gphoto2-list.h gphoto2-result.h gphoto2-setting.h gphoto2-version.h gphoto2-widget.h
        do
                install -m 0644 ${S}/libgphoto2/$X ${STAGING_INCDIR}/gphoto2/$X
        done
        for X in gphoto2-port.h gphoto2-port-info-list.h gphoto2-port-log.h gphoto2-port-version.h gphoto2-port-portability.h gphoto2-port-result.h
        do
                install -m 0644 ${S}/libgphoto2_port/libgphoto2_port/$X ${STAGING_INCDIR}/gphoto2/$X
        done
}

SRC_URI[md5sum] = "a201650482bbc2edbd948449cf4d3f5a"
SRC_URI[sha256sum] = "98af9747179c4d284104fc155b624c6cda4e91a554de0a04e6a6878c0e852c63"
