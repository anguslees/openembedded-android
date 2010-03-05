# Cross packages are built indirectly via dependency,
# no need for them to be a direct target of 'world'
EXCLUDE_FROM_WORLD = "1"

# In order to keep TARGET_PREFIX decoupled from TARGET_SYS, let's force the
# binary names to match the former, rather than relying on autoconf's implicit
# prefixing based on the latter.
EXTRA_OECONF_append = " --program-prefix=${TARGET_PREFIX}"

# Save PACKAGE_ARCH before changing HOST_ARCH
OLD_PACKAGE_ARCH := "${PACKAGE_ARCH}"
PACKAGE_ARCH = "${OLD_PACKAGE_ARCH}"
# Also save BASE_PACKAGE_ARCH since HOST_ARCH can influence it
OLD_BASE_PACKAGE_ARCH := "${BASE_PACKAGE_ARCH}"
BASE_PACKAGE_ARCH = "${OLD_BASE_PACKAGE_ARCH}"

PACKAGES = ""

HOST_ARCH = "${BUILD_ARCH}"
HOST_VENDOR = "${BUILD_VENDOR}"
HOST_OS = "${BUILD_OS}"
HOST_PREFIX = "${BUILD_PREFIX}"
HOST_CC_ARCH = "${BUILD_CC_ARCH}"
HOST_EXEEXT = "${BUILD_EXEEXT}"
BASEPKG_HOST_SYS = "${HOST_ARCH}${HOST_VENDOR}-${HOST_OS}"

CPPFLAGS = "${BUILD_CPPFLAGS}"
CFLAGS = "${BUILD_CFLAGS}"
CXXFLAGS = "${BUILD_CFLAGS}"
LDFLAGS = "${BUILD_LDFLAGS}"
LDFLAGS_build-darwin = "-L${STAGING_LIBDIR_NATIVE}"

TOOLCHAIN_OPTIONS = ""

# Overrides for paths
prefix = "${CROSS_DIR}"
base_prefix = "${prefix}"
exec_prefix = "${prefix}"
base_sbindir = "${base_prefix}/bin"
sbindir = "${exec_prefix}/bin"

do_stage () {
	oe_runmake install
}

do_install () {
	:
}
