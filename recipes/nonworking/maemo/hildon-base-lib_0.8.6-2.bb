PR = "r0"
LICENSE = 	"LGPL"

DEPENDS = "gtk+-2.6.4-1.osso7 outo"

SRC_URI = 	"http://repository.maemo.org/pool/maemo/ossw/source/h/hildon-base-lib/hildon-base-lib_${PV}.tar.gz"

S =	"${WORKDIR}/hildon-base-lib-0.8.6"
EXTRA_OECONF =	"--enable-shared --disable-gtk-doc"

inherit pkgconfig autotools

do_stage() {
	install -d ${STAGING_INCDIR}/hildon-base-lib
	install -d ${STAGING_LIBDIR}
	install -m 644 hildon-base-dnotify.h  hildon-base-types.h ${STAGING_INCDIR}/hildon-base-lib
	install  .libs/libhildonbase.so ${STAGING_LIBDIR}/libhildonbase.so
	install  .libs/libhildonbase.so.0 ${STAGING_LIBDIR}/libhildonbase.so.0
	install  .libs/libhildonbase.so.0.0.1 ${STAGING_LIBDIR}/libhildonbase.so.0.0.1
}

SRC_URI[md5sum] = "388d916894122be839ba09d804eefeb5"
SRC_URI[sha256sum] = "c8eabb77f72efaedb2ed4f1f18e2f3ca84164e0a9fa4dd313a23e3b0cc1ca3d4"
