require glibc.inc
PR = "${INC_PR}.0"

PACKAGES_DYNAMIC = "libc6*"
RPROVIDES_${PN}-dev = "libc6-dev virtual-libc-dev"

# the -isystem in bitbake.conf screws up glibc do_stage
BUILD_CPPFLAGS = "-I${STAGING_INCDIR_NATIVE}"
TARGET_CPPFLAGS = "-I${STAGING_DIR_TARGET}${layout_includedir}"


FILESDIR = "${@os.path.dirname(bb.data.getVar('FILE',d,1))}/glibc-2.4"

GLIBC_ADDONS ?= "ports,nptl,libidn"

GLIBC_BROKEN_LOCALES = " _ER _ET so_ET yn_ER sid_ET tr_TR mn_MN gez_ET gez_ER bn_BD te_IN"

#
# For now, we will skip building of a gcc package if it is a uclibc one
# and our build is not a uclibc one, and we skip a glibc one if our build
# is a uclibc build.
#
# See the note in gcc/gcc_3.4.0.oe
#

python __anonymous () {
    import bb, re
    uc_os = (re.match('.*uclibc$', bb.data.getVar('TARGET_OS', d, 1)) != None)
    if uc_os:
        raise bb.parse.SkipPackage("incompatible with target %s" %
                                   bb.data.getVar('TARGET_OS', d, 1))
}

RDEPENDS_${PN}-dev = "linux-libc-headers-dev"

SRC_URI = "\
  ftp://ftp.gnu.org/pub/gnu/glibc/glibc-${PV}.tar.bz2;name=archive \
  ftp://ftp.gnu.org/pub/gnu/glibc/glibc-ports-${PV}.tar.bz2;name=ports \
  ftp://ftp.gnu.org/pub/gnu/glibc/glibc-libidn-${PV}.tar.bz2;name=libidn \
  file://arm-memcpy.patch \
  file://arm-longlong.patch \
  file://fhs-linux-paths.patch \
  file://dl-cache-libcmp.patch \
  file://nptl-crosscompile.patch \
  file://glibc-2.5-local-dynamic-resolvconf.patch;striplevel=0 \
  file://glibc-check_pf.patch;striplevel=0 \
  file://zecke-sane-readelf.patch \
  file://ldd-unbash.patch \
  file://generic-bits_select.h \
  file://generic-bits_types.h \
  file://generic-bits_typesizes.h \
  file://generic-bits_time.h \
  file://etc/ld.so.conf \
  file://generate-supported.mk \
  file://glibc-2.6.1-RTLD_SINGLE_THREAD_P-1.patch \
  file://glibc-2.6.1-use-short-for-fnstsw.patch \
  file://glibc-use-isystem-include-fixed.patch \
  file://glibc-arm-no-asm-page.patch \
  file://armv4t-interworking.patch \
  file://march-i686.patch;striplevel=0 \
"

SRC_URI_append_ep9312 = "\
  file://glibc-crunch-endian-littleword-littlebyte.patch \
  file://glibc-crunch-eabi-setjmp_longjmp.patch \
  file://glibc-crunch-eabi-unwind.patch \
  file://glibc-crunch-eabi.patch \
  file://glibc-crunch-eabi-force.patch \
  file://glibc-crunch-eabi-fraiseexcpt.patch \
"

# Build fails on sh3 and sh4 without additional patches
SRC_URI_append_sh3 = " file://no-z-defs.patch"
SRC_URI_append_sh4 = " file://no-z-defs.patch"

# PowerPC Patches to add support for soft-float
SRC_URI_append_powerpc = "\
  file://powerpc-sqrt-hack.diff \
  file://glibc-2.6.1-powerpc-nofpu.patch \
"

S = "${WORKDIR}/glibc-${PV}"
B = "${WORKDIR}/build-${TARGET_SYS}"

EXTRA_OECONF = "\
  --enable-kernel=${OLDEST_KERNEL} \
  --without-cvs --disable-profile --disable-debug --without-gd \
  --enable-clocale=gnu \
  --enable-add-ons=${GLIBC_ADDONS} \
  --with-headers=${STAGING_INCDIR} \
  --without-selinux \
  ${GLIBC_EXTRA_OECONF} \
"

EXTRA_OECONF += "${@get_glibc_fpu_setting(bb, d)}"

do_munge() {
	# Integrate ports and libidn into tree
	mv ${WORKDIR}/glibc-ports-${PV} ${S}/ports
	mv ${WORKDIR}/glibc-libidn-${PV} ${S}/libidn

	# Ports isn't really working... Fix it
	# Some of this is rather dirty, but it seems to be the only
	# quick way to get this cruft to compile
	rm -rf ${S}/ports/sysdeps/unix/sysv/linux/arm/linuxthreads
	ln -s nptl ${S}/ports/sysdeps/unix/sysv/linux/arm/linuxthreads
	cp ${S}/nptl/sysdeps/pthread/bits/sigthread.h ${S}/ports/sysdeps/unix/sysv/linux/arm/bits/
	cp ${S}/sysdeps/unix/sysv/linux/i386/bits/wchar.h ${S}/ports/sysdeps/unix/sysv/linux/arm/bits/
	cp ${S}/sysdeps/wordsize-32/bits/wordsize.h ${S}/ports/sysdeps/unix/sysv/linux/arm/bits/
	cp ${WORKDIR}/generic-bits_select.h ${S}/ports/sysdeps/unix/sysv/linux/arm/bits/select.h
	cp ${WORKDIR}/generic-bits_types.h ${S}/ports/sysdeps/unix/sysv/linux/arm/bits/types.h
	cp ${WORKDIR}/generic-bits_typesizes.h ${S}/ports/sysdeps/unix/sysv/linux/arm/bits/typesizes.h
	cp ${WORKDIR}/generic-bits_time.h ${S}/ports/sysdeps/unix/sysv/linux/arm/bits/time.h
	# Copy in generic stuff for not yet implemented headers
	for i in ${S}/bits/*.h; do
		F=`basename $i`
		[ "$F" = "local_lim.h" ] && continue
		[ "$F" = "errno.h" ] && continue
		test -e ${S}/ports/sysdeps/unix/sysv/linux/arm/bits/$F || test -e ${S}/ports/sysdeps/arm/bits/$F || test -e ${S}/sysdeps/unix/sysv/linux/bits/$F || test -e ${S}/sysdeps/ieee754/bits/$F || cp $i ${S}/ports/sysdeps/unix/sysv/linux/arm/bits/
	done
	# This is harmful; we need to get the one from nptl/sysdeps/pthreads
	rm -f ${S}/ports/sysdeps/unix/sysv/linux/arm/bits/libc-lock.h
	# Obsoleted by sysdeps/arm/{fpu,eabi}/bits/fenv.h
	rm -f ${S}/ports/sysdeps/unix/sysv/linux/arm/bits/fenv.h
	# Obsoleted by sysdeps/gnu/bits/utmp.h
	rm -f ${S}/ports/sysdeps/unix/sysv/linux/arm/bits/utmp.h
}

addtask munge before do_patch after do_unpack


do_configure () {
# override this function to avoid the autoconf/automake/aclocal/autoheader
# calls for now
# don't pass CPPFLAGS into configure, since it upsets the kernel-headers
# version check and doesn't really help with anything
	if [ -z "`which rpcgen`" ]; then
		echo "rpcgen not found.  Install glibc-devel."
		exit 1
	fi
	(cd ${S} && gnu-configize) || die "failure in running gnu-configize"
	CPPFLAGS="" oe_runconf
}

rpcsvc = "bootparam_prot.x nlm_prot.x rstat.x \
	  yppasswd.x klm_prot.x rex.x sm_inter.x mount.x \
	  rusers.x spray.x nfs_prot.x rquota.x key_prot.x"

do_compile () {
	# -Wl,-rpath-link <staging>/lib in LDFLAGS can cause breakage if another glibc is in staging
	unset LDFLAGS
	base_do_compile
	(
		cd ${S}/sunrpc/rpcsvc
		for r in ${rpcsvc}; do
			h=`echo $r|sed -e's,\.x$,.h,'`
			rpcgen -h $r -o $h || oewarn "unable to generate header for $r"
		done
	)
}

require glibc-stage.inc

require glibc-package.inc

SRC_URI[archive.md5sum] = "11cf6d3fc86dbe0890b8d00372eb6286"
SRC_URI[archive.sha256sum] = "3ded3a3c3ba2cf02d72479a5cc0829c7c261a9d0934e49a79233de9fa276ec22"
SRC_URI[ports.md5sum] = "53d88ca624642dd267752ccce77b19d0"
SRC_URI[ports.sha256sum] = "d094028bc6d6691f56b4efeff7cd7e1c7ca10733e0cb5efc36e8fb08d8324bf1"
SRC_URI[libidn.md5sum] = "503f1315afd808728ebaa75b3d87a7d9"
SRC_URI[libidn.sha256sum] = "67c98ca1299f5f25eaece256d033e0e63bcf6876b920ca62a1fe61ac62c5c451"
