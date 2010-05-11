require python.inc
DEPENDS = "openssl-native bzip2-full-native zlib-native readline-native"
PR = "${INC_PR}.2"

SRC_URI = "\
  http://www.python.org/ftp/python/${PV}/Python-${PV}.tar.bz2 \
  file://00-fix-bindir-libdir-for-cross.patch;patch=1 \
  file://04-default-is-optimized.patch;patch=1 \
  file://05-enable-ctypes-cross-build.patch;patch=1 \
  file://10-distutils-fix-swig-parameter.patch;patch=1 \
  file://11-distutils-never-modify-shebang-line.patch;patch=1 \
  file://12-distutils-prefix-is-inside-staging-area.patch;patch=1 \
  file://debug.patch;patch=1 \
  file://nohostlibs.patch;patch=1 \
"
S = "${WORKDIR}/Python-${PV}"

inherit native

EXTRA_OEMAKE = '\
  BUILD_SYS="" \
  HOST_SYS="" \
  LIBC="" \
  STAGING_LIBDIR=${STAGING_LIBDIR_NATIVE} \
  STAGING_INCDIR=${STAGING_INCDIR_NATIVE} \
'

do_stage_append() {
	install -m 0755 Parser/pgen ${STAGING_BINDIR_NATIVE}/pgen
}

SRC_URI[md5sum] = "e81c2f0953aa60f8062c05a4673f2be0"
SRC_URI[sha256sum] = "cf153f10ba6312a8303ceb01bed834a2786d28aa89c7d73dba64714f691628f6"
