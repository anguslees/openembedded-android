require python.inc
DEPENDS = "python-native db gdbm openssl readline sqlite3 tcl zlib\
           ${@base_contains('DISTRO_FEATURES', 'tk', 'tk', '', d)}"
DEPENDS_sharprom = "python-native db readline zlib gdbm openssl"
# set to .0 on every increase of INC_PR
PR = "${INC_PR}.1"

SRC_URI = "\
  http://www.python.org/ftp/python/${PV}/Python-${PV}.tar.bz2;name=archive \
  file://00-fix-bindir-libdir-for-cross.patch \
  file://01-use-proper-tools-for-cross-build.patch \
  file://02-remove-test-for-cross.patch \
  file://03-fix-tkinter-detection.patch \
  file://04-default-is-optimized.patch \
  file://05-enable-ctypes-cross-build.patch \
  file://06-ctypes-libffi-fix-configure.patch \
  file://99-ignore-optimization-flag.patch \
  \
# not yet pushed forward
# sitebranding
  \
#  file://05-install.patch \
#  file://06-fix-urllib-exception.patch \
#  file://16-bug1179-imageop.patch \
#  file://13-set-wakeup-fix.patch \
  \
  file://sitecustomize.py \
"
SRC_URI[archive.md5sum] = "fee5408634a54e721a93531aba37f8c1"
SRC_URI[archive.sha256sum] = "dad8d5575144a210d5cc4fdbc40b8a26386e9cdb1ef58941bec0be02c7cb9d89"

S = "${WORKDIR}/Python-${PV}"

inherit autotools

# The 3 lines below are copied from the libffi recipe, ctypes ships its own copy of the libffi sources
#Somehow gcc doesn't set __SOFTFP__ when passing -mfloatabi=softp :(
TARGET_CC_ARCH_append_armv6 = " -D__SOFTFP__"
TARGET_CC_ARCH_append_armv7a = " -D__SOFTFP__"

do_configure_prepend() {
	autoreconf -Wcross --verbose --install --force --exclude=autopoint Modules/_ctypes/libffi || oenote "_ctypes failed to autoreconf"
}

#
# Copy config.h and an appropriate Makefile for distutils.sysconfig,
# which laters uses the information out of these to compile extensions
#
do_compile_prepend() {
	install -d ${STAGING_INCDIR}/python${PYTHON_MAJMIN}/
	install -d ${STAGING_LIBDIR}/python${PYTHON_MAJMIN}/config/
	install -m 0644 pyconfig.h ${STAGING_INCDIR}/python${PYTHON_MAJMIN}/
	install -m 0644 Makefile Makefile.orig
	install -m 0644 Makefile Makefile.backup
	sed -e 's,${includedir},${STAGING_INCDIR},' < Makefile.backup > Makefile
	install -m 0644 Makefile Makefile.backup
	sed -e 's,${libdir},${STAGING_LIBDIR},' < Makefile.backup > Makefile
	install -m 0644 Makefile ${STAGING_LIBDIR}/python${PYTHON_MAJMIN}/config/
}

do_compile() {
	oe_runmake HOSTPGEN=${STAGING_BINDIR_NATIVE}/pgen \
		HOSTPYTHON=${STAGING_BINDIR_NATIVE}/python \
		STAGING_LIBDIR=${STAGING_LIBDIR} \
		STAGING_INCDIR=${STAGING_INCDIR} \
		BUILD_SYS=${BUILD_SYS} HOST_SYS=${HOST_SYS} \
		OPT="${CFLAGS}" libpython${PYTHON_MAJMIN}.so

	oe_libinstall -so libpython${PYTHON_MAJMIN} ${STAGING_LIBDIR}

	oe_runmake HOSTPGEN=${STAGING_BINDIR_NATIVE}/pgen \
		HOSTPYTHON=${STAGING_BINDIR_NATIVE}/python \
		STAGING_LIBDIR=${STAGING_LIBDIR} \
		STAGING_INCDIR=${STAGING_INCDIR} \
		BUILD_SYS=${BUILD_SYS} HOST_SYS=${HOST_SYS} \
		RUNSHARED= OPT="${CFLAGS}"
}

do_stage() {
	install -m 0644 Include/*.h ${STAGING_INCDIR}/python${PYTHON_MAJMIN}/
	oe_libinstall -a -so libpython${PYTHON_MAJMIN} ${STAGING_LIBDIR}
}

do_install() {
	install -m 0644 Makefile.orig Makefile
	
	oe_runmake HOSTPGEN=${STAGING_BINDIR_NATIVE}/pgen \
		HOSTPYTHON=${STAGING_BINDIR_NATIVE}/python \
		STAGING_LIBDIR=${STAGING_LIBDIR} \
		STAGING_INCDIR=${STAGING_INCDIR} \
		BUILD_SYS=${BUILD_SYS} HOST_SYS=${HOST_SYS} \
		DESTDIR=${D} LIBDIR=${libdir} RUNSHARED= install

	install -m 0644 ${WORKDIR}/sitecustomize.py ${D}/${libdir}/python${PYTHON_MAJMIN}

	# remove hardcoded ccache, see http://bugs.openembedded.net/show_bug.cgi?id=4144
	sed -i -e s,ccache,'$(CCACHE)', ${D}/${libdir}/python${PYTHON_MAJMIN}/config/Makefile
}

require python-${PYTHON_MAJMIN}-manifest.inc

# manual dependency additions
RPROVIDES_python-core = "python"
RRECOMMENDS_python-core = "python-readline"
RRECOMMENDS_python-crypt = "openssl"

# add sitecustomize
FILES_python-core += "${libdir}/python${PYTHON_MAJMIN}/sitecustomize.py"
# ship 2to3
FILES_python-core += "${bindir}/2to3"

# package libpython2
PACKAGES =+ "libpython2"
FILES_libpython2 = "${libdir}/libpython*.so*"

# additional stuff -dev

FILES_${PN}-dev = "\
  ${includedir} \
  ${libdir}/lib*${SOLIBSDEV} \
  ${libdir}/*.la \
  ${libdir}/*.a \
  ${libdir}/*.o \
  ${libdir}/pkgconfig \
  ${base_libdir}/*.a \
  ${base_libdir}/*.o \
  ${datadir}/aclocal \
  ${datadir}/pkgconfig \
"

# catch debug extensions (isn't that already in python-core-dbg?)
FILES_python-dbg += "${libdir}/python${PYTHON_MAJMIN}/lib-dynload/.debug"

# catch all the rest (unsorted)
PACKAGES += "python-misc"
FILES_python-misc = "${libdir}/python${PYTHON_MAJMIN}"

# catch manpage
PACKAGES += "python-man"
FILES_python-man = "${datadir}/man"
