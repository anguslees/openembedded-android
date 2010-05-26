DESCRIPTION = "A library for loose coupling of C++ method calls"
SECTION = "libs"
PRIORITY = "optional"
PR = "r1"
LICENSE = "GPL LGPL"
SRC_URI = "ftp://ftp.gnome.org/pub/GNOME/sources/libsigc++/2.0/libsigc++-${PV}.tar.gz \
	   file://define_sigc_api.patch"
S = "${WORKDIR}/libsigc++-${PV}"

inherit autotools pkgconfig

FILES_${PN}-dev += "${libdir}/sigc++-*/"

# acpaths = ""

do_stage() {
	install -d ${STAGING_LIBDIR}/sigc++-2.0/include
	install -m 0644 sigc++config.h ${STAGING_LIBDIR}/sigc++-2.0/include/
	oe_libinstall -so -C sigc++ libsigc-2.0 ${STAGING_LIBDIR}

	install -d ${STAGING_INCDIR}/sigc++-2.0/sigc++
	for f in sigc++/*.h
	do
		install -m 0644 $f ${STAGING_INCDIR}/sigc++-2.0/sigc++/
	done
	install -d ${STAGING_INCDIR}/sigc++-2.0/sigc++/functors
	for f in sigc++/functors/*.h
	do
		install -m 0644 $f ${STAGING_INCDIR}/sigc++-2.0/sigc++/functors
	done

	install -d ${STAGING_INCDIR}/sigc++-2.0/sigc++/adaptors
	for f in sigc++/adaptors/*.h
	do
		install -m 0644 $f ${STAGING_INCDIR}/sigc++-2.0/sigc++/adaptors
	done
	install -d ${STAGING_INCDIR}/sigc++-2.0/sigc++/adaptors/lambda
	for f in sigc++/adaptors/lambda/*.h
	do
		install -m 0644 $f ${STAGING_INCDIR}/sigc++-2.0/sigc++/adaptors/lambda
	done


}


SRC_URI[md5sum] = "9eab84e54790093be3aa93fb4163c4b9"
SRC_URI[sha256sum] = "25d21557dccebbed2c7ff704fded0399194164d0a5e318791b4594a7db1cfa6b"
