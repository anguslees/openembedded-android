DESCRIPTION = "A GNU collection of binary utilities"
HOMEPAGE = "http://www.gnu.org/software/binutils/"
SECTION = "devel"
LICENSE = "GPL"
INC_PR = "r5"
PR = "${INC_PR}.1"

inherit autotools gettext

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
     file://objdump_fix.patch \
     file://binutils-100_cflags_for_build.patch \
     file://binutils-2.15.91.0.1-uclibc-100-conf.patch \
     file://binutils-2.15.90.0.3-uclibc-200-build_modules.patch \
     file://binutils-2.15-allow-gcc-4.0.patch"
S = "${WORKDIR}/binutils-${PV}"
B = "${S}/build.${HOST_SYS}.${TARGET_SYS}"

EXTRA_OECONF = "--program-prefix=${TARGET_PREFIX} \
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

export CC_FOR_HOST = "${CCACHE} ${HOST_PREFIX}gcc ${HOST_CC_ARCH}"
export CXX_FOR_HOST = "${CCACHE} ${HOST_PREFIX}gcc ${HOST_CC_ARCH}"

export CC_FOR_BUILD = "${BUILD_CC}"
export CPP_FOR_BUILD = "${BUILD_CPP}"
export CFLAGS_FOR_BUILD = "${BUILD_CFLAGS}"

export CC = "${CCACHE} ${HOST_PREFIX}gcc ${HOST_CC_ARCH}"

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

SRC_URI[md5sum] = "4a4cde8e5d0c97249bf6933f095813fe"
SRC_URI[sha256sum] = "c4ad801a7ed5a4e3d5d943d73c82f0af75998c95b69184dc884460bf40e48ee9"
