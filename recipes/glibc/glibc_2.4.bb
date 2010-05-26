require glibc.inc

PR = "${INC_PR}.0"

#add the hosts that are confirmed to be working to COMPATIBLE_HOSTi
COMPATIBLE_HOST = '(i.86.*-linux|sh.*-linux)'
DEFAULT_PREFERENCE_arm = "-1"

# the -isystem in bitbake.conf screws up glibc do_stage
BUILD_CPPFLAGS = "-I${STAGING_INCDIR_NATIVE}"
TARGET_CPPFLAGS = "-I${STAGING_DIR_TARGET}${layout_includedir}"


FILESDIR = "${@os.path.dirname(bb.data.getVar('FILE',d,1))}/glibc-2.4"

GLIBC_ADDONS ?= "ports,nptl,libidn"

GLIBC_BROKEN_LOCALES = "sid_ET tr_TR mn_MN gez_ET gez_ER bn_BD te_IN"

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
RPROVIDES_${PN}-dev += "libc-dev virtual-libc-dev"

#	   file://noinfo.patch
#	   file://ldconfig.patch;striplevel=0
#	   file://arm-machine-gmon.patch;striplevel=0 \
#	   \
#	   file://arm-ioperm.patch;striplevel=0 \
#	   file://ldd.patch;striplevel=0 \
SRC_URI = "ftp://ftp.gnu.org/pub/gnu/glibc/glibc-2.4.tar.bz2;name=archive \
	   ftp://ftp.gnu.org/pub/gnu/glibc/glibc-ports-2.4.tar.bz2;name=ports \
	   ftp://ftp.gnu.org/pub/gnu/glibc/glibc-libidn-2.4.tar.bz2;name=libidn \
           file://arm-memcpy.patch \
           file://arm-longlong.patch \
           file://fhs-linux-paths.patch \
           file://dl-cache-libcmp.patch \
           file://ldsocache-varrun.patch \
           file://nptl-crosscompile.patch \
	   file://glibc-2.4-compile.patch \
	   file://glibc-2.4-openat-3.patch \
	   file://fixup-aeabi-syscalls.patch \
	   file://zecke-sane-readelf.patch \
	   file://arm_eabi_ipc_fix.patch \
	   file://generic-bits_select.h \
	   file://generic-bits_types.h \
	   file://generic-bits_typesizes.h \
	   file://generic-bits_time.h \
           file://etc/ld.so.conf \
           file://generate-supported.mk"


# Build fails on sh3 and sh4 without additional patches
SRC_URI_append_sh3 = " file://no-z-defs.patch"
SRC_URI_append_sh4 = " file://no-z-defs.patch"

S = "${WORKDIR}/glibc-2.4"
B = "${WORKDIR}/build-${TARGET_SYS}"

EXTRA_OECONF = "--enable-kernel=${OLDEST_KERNEL} \
	        --without-cvs --disable-profile --disable-debug --without-gd \
		--enable-clocale=gnu \
	        --enable-add-ons=${GLIBC_ADDONS} \
		--with-headers=${STAGING_INCDIR} \
		--without-selinux \
		${GLIBC_EXTRA_OECONF}"

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

SRC_URI[archive.md5sum] = "7e9a88dcd41fbc53801dbe5bdacaf245"
SRC_URI[archive.sha256sum] = "27aaaaa78b4ab5da76bb29385dc9be087ba7b855a9102eaaa78ce3ec5e2e7fcd"
SRC_URI[ports.md5sum] = "7bb1b35654e94994925e66676acabd4e"
SRC_URI[ports.sha256sum] = "2fbbcaad8a9f8560485c398a9066959fe2713c8bc7e653ec476fb56fed21d19a"
SRC_URI[libidn.md5sum] = "e2d892b40d654c523ab26a26b7dd86a1"
SRC_URI[libidn.sha256sum] = "2f29ec2b2b2f75f787a1eed8fdea20aff212dc3bad97651ca376914c49181977"
