require linux-libc-headers.inc

HOMEPAGE = "http://ep09.pld-linux.org/~mmazur/linux-libc-headers/"
# license note from the package:
#   Linux-libc-headers are derived from linux kernel headers. For license of a
#   particular header, check it's content, and if copyright notice isn't present,
#   standard linux kernel license applies.
# since we assume GPL for linux i think we can also assume it here
INHIBIT_DEFAULT_DEPS = "1"
PR = "r8"

# NOTE: no need to package these headers, since the c library includes them.
PACKAGES = ""

SRC_URI = "http://ep09.pld-linux.org/~mmazur/linux-libc-headers/linux-libc-headers-${PV}.tar.bz2 \
	file://mips-asm-atomic-system.diff;striplevel=0 \
	file://keyboard.patch"

S = "${WORKDIR}/linux-libc-headers-${PV}"

do_configure () {
	case ${TARGET_ARCH} in
		alpha*)   ARCH=alpha ;;
		arm*)     ARCH=arm ;;
		cris*)    ARCH=cris ;;
		hppa*)    ARCH=parisc ;;
		i*86*)    ARCH=i386 ;;
		ia64*)    ARCH=ia64 ;;
		mips*)    ARCH=mips ;;
		m68k*)    ARCH=m68k ;;
		powerpc*) ARCH=ppc ;;
		s390*)    ARCH=s390 ;;
		sh*)      ARCH=sh ;;
		sparc64*) ARCH=sparc64 ;;
		sparc*)   ARCH=sparc ;;
		x86_64*)  ARCH=x86_64 ;;
	esac
	if test !  -e include/asm-$ARCH; then
		oefatal unable to create asm symlink in kernel headers
	fi
	cp -pPR "include/asm-$ARCH" "include/asm"
	if test "$ARCH" = "arm"; then
		cp -pPR include/asm/arch-ebsa285 include/asm/arch
	elif test "$ARCH" = "sh"; then
		cp -pPR include/asm/cpu-${TARGET_ARCH} include/asm/cpu || die "unable to create include/asm/cpu"
	fi
}

do_stage () {
	install -d ${STAGING_INCDIR}
	rm -rf ${STAGING_INCDIR}/linux ${STAGING_INCDIR}/asm
	cp -pfLR include/linux ${STAGING_INCDIR}/
	cp -pfLR include/asm ${STAGING_INCDIR}/
}

SRC_URI[md5sum] = "a75c264f90b07b4f3ba05febc7386f4b"
SRC_URI[sha256sum] = "384c0e97100170b4566a0b335a18246c42c76df682377cae6ed1c72be8aadc0e"
