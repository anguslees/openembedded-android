OVERRIDES .= ":libc-bionic"

PREFERRED_PROVIDER_virtual/libc = "bionic"
PREFERRED_PROVIDER_virtual/${TARGET_PREFIX}libc-initial = "bionic"
PREFERRED_PROVIDER_virtual/${TARGET_PREFIX}libc-for-gcc = "bionic"
