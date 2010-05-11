require python.inc
DEPENDS = "openssl-native bzip2-full-native zlib-native readline-native"
PR = "${INC_PR}.3"

SRC_URI = "\
  http://www.python.org/ftp/python/${PV}/Python-${PV}.tar.bz2;name=archive \
  file://00-fix-bindir-libdir-for-cross.patch;patch=1 \
  file://04-default-is-optimized.patch;patch=1 \
  file://05-enable-ctypes-cross-build.patch;patch=1 \
  file://06-ctypes-libffi-fix-configure.patch;patch=1 \
  file://10-distutils-fix-swig-parameter.patch;patch=1 \
  file://11-distutils-never-modify-shebang-line.patch;patch=1 \
  file://12-distutils-prefix-is-inside-staging-area.patch;patch=1 \
  file://debug.patch;patch=1 \
  file://nohostlibs.patch;patch=1 \
"
S = "${WORKDIR}/Python-${PV}"

SRC_URI[archive.md5sum] = "fee5408634a54e721a93531aba37f8c1"
SRC_URI[archive.sha256sum] = "dad8d5575144a210d5cc4fdbc40b8a26386e9cdb1ef58941bec0be02c7cb9d89"

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
