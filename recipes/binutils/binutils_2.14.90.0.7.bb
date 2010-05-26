SECTION = "devel"
LICENSE = "GPL"
inherit autotools gettext

DESCRIPTION = "A GNU collection of binary utilities"
HOMEPAGE = "http://www.gnu.org/software/binutils/"
LICENSE = "GPL"
INC_PR = "r6"
PR = "${INC_PR}.1"

PACKAGES = "${PN} ${PN}-dev ${PN}-doc ${PN}-symlinks"

FILES_${PN} = " \
	${bindir}/${TARGET_PREFIX}* \
	${libdir}/lib*-*.so \
	${prefix}/${TARGET_SYS}/bin/*"

FILES_${PN}-dev = " \
	${includedir} \
	${libdir}/*.a \
	${libdir}/*.la \
	${libdir}/libbfd.so \
	${libdir}/libopcodes.so"

FILES_${PN}-symlinks = " \
	${bindir}/addr2line \
	${bindir}/ar \
	${bindir}/as \
	${bindir}/c++filt \
	${bindir}/gprof \
	${bindir}/ld \
	${bindir}/nm \
	${bindir}/objcopy \
	${bindir}/objdump \
	${bindir}/ranlib \
	${bindir}/readelf \
	${bindir}/size \
	${bindir}/strings \
	${bindir}/strip"

SRC_URI = \
    "${KERNELORG_MIRROR}/pub/linux/devel/binutils/binutils-${PV}.tar.bz2 \
     file://ld_makefile.patch \
     file://better_file_error.patch \
     file://signed_char_fix.patch \
     file://binutils-uclibc-001-debian.patch \
     file://binutils-uclibc-100-conf.patch \
     file://binutils-uclibc-200-build_modules.patch \
     file://binutils-uclibc-210-cflags.patch \
     file://binutils-100_cflags_for_build.patch \
     file://plt32trunc.patch \
     file://600-arm-textrel.patch \
     file://binutils-2.14.90.0.7-fix-broken-configure.patch"

S = "${WORKDIR}/binutils-${PV}"
B = "${S}/build.${HOST_SYS}.${TARGET_SYS}"

EXTRA_OECONF = "--with-sysroot=/ \
		--program-prefix=${TARGET_PREFIX} \
		--enable-shared"

# This is necessary due to a bug in the binutils Makefiles
EXTRA_OEMAKE = "configure-build-libiberty all"

export AR = "${HOST_PREFIX}ar"
export AS = "${HOST_PREFIX}as"
export LD = "${HOST_PREFIX}ld"
export NM = "${HOST_PREFIX}nm"
export RANLIB = "${HOST_PREFIX}ranlib"
export OBJCOPY = "${HOST_PREFIX}objcopy"
export OBJDUMP = "${HOST_PREFIX}objdump"

export AR_FOR_TARGET = "${TARGET_PREFIX}ar"
export AS_FOR_TARGET = "${TARGET_PREFIX}as"
export LD_FOR_TARGET = "${TARGET_PREFIX}ld"
export NM_FOR_TARGET = "${TARGET_PREFIX}nm"
export RANLIB_FOR_TARGET = "${TARGET_PREFIX}ranlib"

export CC_FOR_HOST = "${CCACHE} ${HOST_PREFIX}gcc"
export CXX_FOR_HOST = "${CCACHE} ${HOST_PREFIX}gcc"

export CC_FOR_BUILD = "${BUILD_CC}"

export CC = "${CCACHE} ${HOST_PREFIX}gcc"

do_configure () {
	(cd ${S}; gnu-configize) || die "Failed to run gnu-configize"
	oe_runconf
}

do_stage () {
	oe_libinstall -so -a -C opcodes libopcodes ${STAGING_LIBDIR}/
	oe_libinstall -a -C libiberty libiberty ${STAGING_LIBDIR}/
	oe_libinstall -so -a -C bfd libbfd ${STAGING_LIBDIR}/
	install -m 0644 ${S}/include/dis-asm.h ${STAGING_INCDIR}/
	install -m 0644 ${S}/include/symcat.h ${STAGING_INCDIR}/
	install -m 0644 ${S}/include/libiberty.h ${STAGING_INCDIR}/
	install -m 0644 ${S}/include/ansidecl.h ${STAGING_INCDIR}/
	install -m 0644 ${S}/include/bfdlink.h ${STAGING_INCDIR}/
	install -m 0644 bfd/bfd.h ${STAGING_INCDIR}/
}

do_install () {
	autotools_do_install

	# We don't really need these, so we'll remove them...
	rm -rf ${D}${libdir}/ldscripts

	# Fix the /usr/${TARGET_SYS}/bin/* links
	for l in ${D}${prefix}/${TARGET_SYS}/bin/*; do
		rm -f $l
		ln -sf `echo ${prefix}/${TARGET_SYS}/bin \
			| tr -s / \
			| sed -e 's,^/,,' -e 's,[^/]*,..,g'`${bindir}/${TARGET_PREFIX}`basename $l` $l
	done

	# Install the libiberty header
	install -d ${D}${includedir}
	install -m 644 ${S}/include/ansidecl.h ${D}${includedir}
	install -m 644 ${S}/include/libiberty.h ${D}${includedir}

	cd ${D}${bindir}

	# Symlinks for ease of running these on the native target
	for p in ${TARGET_SYS}-* ; do
		ln -sf $p `echo $p | sed -e s,${TARGET_SYS}-,,`
	done
}

SRC_URI[md5sum] = "b5b1608f7308c487c0f3af8e4592a71a"
SRC_URI[sha256sum] = "204c2624b5712a3482d0e774bb84850c1ee6b1ccdfd885abfe1f7c23abf4f5c0"
