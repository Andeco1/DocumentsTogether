// Theme Management System

class ThemeManager {
  constructor() {
    this.currentTheme = this.getStoredTheme() || 'light';
    this.init();
  }

  init() {
    this.applyTheme(this.currentTheme);
    this.bindEvents();
  }

  getStoredTheme() {
    return localStorage.getItem('theme') || 'light';
  }

  setStoredTheme(theme) {
    localStorage.setItem('theme', theme);
  }

  applyTheme(theme) {
    // Remove existing theme classes
    document.documentElement.classList.remove('theme-light', 'theme-dark', 'theme-colorblind');
    
    // Apply new theme
    document.documentElement.classList.add(`theme-${theme}`);
    
    // Update stored theme
    this.setStoredTheme(theme);
    this.currentTheme = theme;
    
    // Update theme selector if it exists
    const themeSelect = document.getElementById('themeSelect');
    if (themeSelect) {
      themeSelect.value = theme;
    }
  }

  switchTheme(theme) {
    if (['light', 'dark', 'colorblind'].includes(theme)) {
      this.applyTheme(theme);
      this.saveToServer(theme);
    }
  }

  async saveToServer(theme) {
    try {
      // Try to get current language from language selector
      const languageSelect = document.getElementById('languageSelect');
      const currentLanguage = languageSelect ? languageSelect.value : 'ru';
      
      const response = await fetch('/api/preferences', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: `theme=${theme}&language=${currentLanguage}`
      });
      
      if (!response.ok) {
        console.warn('Failed to save theme preferences to server');
      }
    } catch (error) {
      console.warn('Error saving theme preferences:', error);
    }
  }

  bindEvents() {
    // Theme switcher dropdown
    const themeSelect = document.getElementById('themeSelect');
    if (themeSelect) {
      themeSelect.addEventListener('change', (e) => {
        this.switchTheme(e.target.value);
      });
    }

    // Keyboard shortcuts
    document.addEventListener('keydown', (e) => {
      // Ctrl/Cmd + Shift + T to cycle themes
      if ((e.ctrlKey || e.metaKey) && e.shiftKey && e.key === 'T') {
        e.preventDefault();
        this.cycleTheme();
      }
    });
  }

  cycleTheme() {
    const themes = ['light', 'dark', 'colorblind'];
    const currentIndex = themes.indexOf(this.currentTheme);
    const nextIndex = (currentIndex + 1) % themes.length;
    this.switchTheme(themes[nextIndex]);
  }

  // Get current theme
  getCurrentTheme() {
    return this.currentTheme;
  }

  // Check if theme is supported
  isThemeSupported(theme) {
    return ['light', 'dark', 'colorblind'].includes(theme);
  }
}

// Initialize theme manager when DOM is loaded
document.addEventListener('DOMContentLoaded', () => {
  window.themeManager = new ThemeManager();
});

// Export for use in other scripts
if (typeof module !== 'undefined' && module.exports) {
  module.exports = ThemeManager;
}
