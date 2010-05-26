LICENSE = "GPL"
DESCRIPTION = "ntop is network top"
SECTION = "console/network"
PRIORITY = "optional"
DEPENDS = "gdbm zlib libpcap libpng gd"
PR = "r1"

SRC_URI = "${SOURCEFORGE_MIRROR}/ntop/ntop-${PV}.tgz \
           file://autotools.patch \
           file://plugins.patch"

inherit autotools

EXTRA_OECONF += " --without-ssl \
                  --with-gd-lib=${STAGING_LIBDIR} \
                  --with-gd-include=${STAGING_INCDIR} \
                  --with-zlib-lib=${STAGING_LIBDIR} \
                  --with-zlib-include=${STAGING_INCDIR} \
                  --with-pcap-lib=${STAGING_LIBDIR} \
                  --with-pcap-include=${STAGING_INCDIR} \
                  --with-libpng-lib=${STAGING_LIBDIR} \
                  --with-libpng-include=${STAGING_INCDIR} \
                  --with-gdbm-lib=${STAGING_LIBDIR} \
                  --with-gdbm-include=${STAGING_INCDIR}"

FILES_ntop_append = " ${libdir}/ntop/plugins/*.so ${libdir}/libntop-*.so \
                       ${libdir}/libntopreport-*.so"
FILES_${PN}-dev = "${includedir} ${libdir}/libntop.so ${libdir}/libntopreport.so \
                   ${libdir}/*.a ${libdir}/libntopreport.a ${libdir}/*.la"
FILES_${PN}-dbg += "${libdir}/ntop/plugins/.debug"

do_configure_prepend () {
	if [ ! -e acinclude.m4 ]; then
		mv acinclude.m4.ntop acinclude.m4
	fi
	rm -f libtool
	cp ${STAGING_BINDIR_NATIVE}/${TARGET_SYS}-libtool libtool
}

SRC_URI[md5sum] = "1ec6055c75f1acbb5d5600492481ef85"
SRC_URI[sha256sum] = "c50cdf2c6cf7309f88c7ec0a56158eca76c80532502b36f3378eeca433423d83"
