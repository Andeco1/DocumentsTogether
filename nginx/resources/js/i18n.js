
class I18nManager {
  constructor() {
    this.currentLanguage = this.getStoredLanguage() || 'ru';
    this.translations = {
      ru: {
        // Navigation
        'nav.home': 'Главная',
        'nav.library': 'Библиотека',
        'nav.login': 'Войти',
        'nav.register': 'Регистрация',
        'nav.profile': 'Профиль',
        'nav.logout': 'Выйти',
        'nav.about': 'О библиотеке',
        
        // Common
        'common.welcome': 'Добро пожаловать',
        'common.search': 'Поиск',
        'common.save': 'Сохранить',
        'common.cancel': 'Отмена',
        'common.delete': 'Удалить',
        'common.edit': 'Изменить',
        'common.add': 'Добавить',
        'common.close': 'Закрыть',
        'common.loading': 'Загрузка...',
        'common.error': 'Ошибка',
        'common.success': 'Успешно',
        
        // Authentication
        'auth.login': 'Вход в систему',
        'auth.register': 'Регистрация',
        'auth.username': 'Имя пользователя',
        'auth.email': 'Электронная почта',
        'auth.password': 'Пароль',
        'auth.confirmPassword': 'Подтвердите пароль',
        'auth.loginButton': 'Войти',
        'auth.registerButton': 'Зарегистрироваться',
        'auth.noAccount': 'Нет аккаунта?',
        'auth.hasAccount': 'Уже есть аккаунт?',
        'auth.loginLink': 'Войти',
        'auth.registerLink': 'Зарегистрироваться',
        'auth.loginSuccess': 'Вы успешно вошли',
        'auth.registerSuccess': 'Регистрация прошла успешно! Теперь вы можете войти',
        'auth.loginError': 'Неверное имя пользователя или пароль',
        'auth.registerError': 'Ошибка при регистрации. Попробуйте снова',
        'auth.passwordMismatch': 'Пароли не совпадают',
        
        // Library
        'library.title': 'Онлайн библиотека',
        'library.greeting': 'Добро пожаловать в библиотеку!',
        'library.searchPlaceholder': 'Поиск по названию или автору',
        'library.searchButton': 'Искать',
        'library.addDocument': 'Добавить документ',
        'library.documentTitle': 'Название',
        'library.documentAuthor': 'Автор',
        'library.documentDescription': 'Описание',
        'library.documentFile': 'Файл',
        'library.documentPublic': 'Сделать публичным',
        'library.documentDetails': 'Подробнее',
        'library.documentDownload': 'Скачать',
        'library.noDocuments': 'Документы не найдены',
        'library.uploadSuccess': 'Документ успешно загружен!',
        'library.uploadError': 'Ошибка загрузки',
        'library.authRequired': 'Войдите в систему для загрузки документов',
        
        // User Info
        'user.guest': 'Гость',
        'user.welcome': 'Добро пожаловать!',
        'user.loginPrompt': 'Войдите для загрузки документов',
        
        // Preferences
        'preferences.title': 'Настройки',
        'preferences.theme': 'Тема',
        'preferences.language': 'Язык',
        'preferences.themeLight': 'Светлая',
        'preferences.themeDark': 'Темная',
        'preferences.themeColorblind': 'Для людей с цветовой слепотой',
        'preferences.languageRu': 'Русский',
        'preferences.languageEn': 'English',
        'preferences.saveButton': 'Сохранить',
        'preferences.saveSuccess': 'Настройки сохранены! Перезагружаем страницу...',
        'preferences.saveError': 'Ошибка сохранения настроек',
        'preferences.authRequired': 'Войдите в систему для изменения настроек',
        
        // Landing Page
        'landing.title': 'eLibrary — онлайн библиотека',
        'landing.heroTitle': 'Онлайн библиотека книг и документов',
        'landing.heroSubtitle': 'Ищите, читайте и скачивайте материалы из публичного каталога',
        'landing.openCatalog': 'Открыть каталог',
        'landing.feature1Title': 'Публичный каталог',
        'landing.feature1Desc': 'Сотни материалов, доступных без регистрации.',
        'landing.feature2Title': 'Поиск',
        'landing.feature2Desc': 'Быстрый поиск по названию и автору.',
        'landing.feature3Title': 'Загрузка файлов',
        'landing.feature3Desc': 'Пополняйте библиотеку собственными материалами.',
        'landing.copyright': '© 2025 eLibrary. Все права защищены.',
        
         // Documents
         'documents.title': 'Мои документы — DocuShare',
         'documents.myDocuments': 'Мои документы',
         'documents.createFile': 'Создать файл',
         'documents.documentName': 'Название документа',
         'documents.edit': 'Изменить',
         'documents.delete': 'Удалить',
         
         // Statistics
         'statistics.title': 'Статистика — DocuShare',
         'statistics.generalStats': 'Общая статистика',
         'statistics.totalUsers': 'Всего пользователей',
         'statistics.totalDocuments': 'Всего документов',
         'statistics.publicDocuments': 'Публичных документов',
         'statistics.privateDocuments': 'Приватных документов',
         'statistics.charts': 'Аналитические графики',
         'statistics.documentsByMonth': 'Документы по месяцам',
         'statistics.documentsByMonthDesc': 'Показывает динамику загрузки документов по месяцам',
         'statistics.userThemes': 'Темы пользователей',
         'statistics.userThemesDesc': 'Распределение пользователей по выбранным темам оформления',
         'statistics.userLanguages': 'Языки пользователей',
         'statistics.userLanguagesDesc': 'Распределение пользователей по выбранным языкам интерфейса',
         'statistics.documentVisibility': 'Видимость документов',
         'statistics.documentVisibilityDesc': 'Соотношение публичных и приватных документов',
         'statistics.userActivity': 'Активность пользователей',
         'statistics.userActivityDesc': 'Активность загрузки документов по дням недели',
         'statistics.download': 'Скачать',
         'statistics.watermarkInfo': 'Информация о графиках',
         'statistics.watermarkDesc': 'Все графики содержат полупрозрачный водяной знак с названием системы и датой генерации. Графики созданы с использованием библиотеки JFreeChart и обработаны с помощью Java Graphics2D API.',
         'statistics.chartTypes': 'Типы графиков: столбчатые диаграммы, круговые диаграммы, линейные графики',
         'statistics.watermarkFeatures': 'Водяные знаки: название системы, дата генерации',
         'statistics.dataSource': 'Источник данных: база данных PostgreSQL с фикстурами'
      },
      en: {
        // Navigation
        'nav.home': 'Home',
        'nav.library': 'Library',
        'nav.login': 'Login',
        'nav.register': 'Register',
        'nav.profile': 'Profile',
        'nav.logout': 'Logout',
        'nav.about': 'About Library',
        
        // Common
        'common.welcome': 'Welcome',
        'common.search': 'Search',
        'common.save': 'Save',
        'common.cancel': 'Cancel',
        'common.delete': 'Delete',
        'common.edit': 'Edit',
        'common.add': 'Add',
        'common.close': 'Close',
        'common.loading': 'Loading...',
        'common.error': 'Error',
        'common.success': 'Success',
        
        // Authentication
        'auth.login': 'Login',
        'auth.register': 'Register',
        'auth.username': 'Username',
        'auth.email': 'Email',
        'auth.password': 'Password',
        'auth.confirmPassword': 'Confirm Password',
        'auth.loginButton': 'Login',
        'auth.registerButton': 'Register',
        'auth.noAccount': 'No account?',
        'auth.hasAccount': 'Already have an account?',
        'auth.loginLink': 'Login',
        'auth.registerLink': 'Register',
        'auth.loginSuccess': 'You have successfully logged in',
        'auth.registerSuccess': 'Registration successful! You can now login',
        'auth.loginError': 'Invalid username or password',
        'auth.registerError': 'Registration error. Please try again',
        'auth.passwordMismatch': 'Passwords do not match',
        
        // Library
        'library.title': 'Online Library',
        'library.greeting': 'Welcome to the library!',
        'library.searchPlaceholder': 'Search by title or author',
        'library.searchButton': 'Search',
        'library.addDocument': 'Add Document',
        'library.documentTitle': 'Title',
        'library.documentAuthor': 'Author',
        'library.documentDescription': 'Description',
        'library.documentFile': 'File',
        'library.documentPublic': 'Make public',
        'library.documentDetails': 'Details',
        'library.documentDownload': 'Download',
        'library.noDocuments': 'No documents found',
        'library.uploadSuccess': 'Document uploaded successfully!',
        'library.uploadError': 'Upload error',
        'library.authRequired': 'Please login to upload documents',
        
        // User Info
        'user.guest': 'Guest',
        'user.welcome': 'Welcome!',
        'user.loginPrompt': 'Login to upload documents',
        
        // Preferences
        'preferences.title': 'Settings',
        'preferences.theme': 'Theme',
        'preferences.language': 'Language',
        'preferences.themeLight': 'Light',
        'preferences.themeDark': 'Dark',
        'preferences.themeColorblind': 'Colorblind',
        'preferences.languageRu': 'Русский',
        'preferences.languageEn': 'English',
        'preferences.saveButton': 'Save',
        'preferences.saveSuccess': 'Settings saved! Reloading page...',
        'preferences.saveError': 'Error saving settings',
        'preferences.authRequired': 'Please login to change settings',
        
        // Landing Page
        'landing.title': 'eLibrary — Online Library',
        'landing.heroTitle': 'Online Library of Books and Documents',
        'landing.heroSubtitle': 'Search, read and download materials from the public catalog',
        'landing.openCatalog': 'Open Catalog',
        'landing.feature1Title': 'Public Catalog',
        'landing.feature1Desc': 'Hundreds of materials available without registration.',
        'landing.feature2Title': 'Search',
        'landing.feature2Desc': 'Quick search by title and author.',
        'landing.feature3Title': 'File Upload',
        'landing.feature3Desc': 'Contribute to the library with your own materials.',
        'landing.copyright': '© 2025 eLibrary. All rights reserved.',
        
         // Documents
         'documents.title': 'My Documents — DocuShare',
         'documents.myDocuments': 'My Documents',
         'documents.createFile': 'Create File',
         'documents.documentName': 'Document Name',
         'documents.edit': 'Edit',
         'documents.delete': 'Delete',
         
         // Statistics
         'statistics.title': 'Statistics — DocuShare',
         'statistics.generalStats': 'General Statistics',
         'statistics.totalUsers': 'Total Users',
         'statistics.totalDocuments': 'Total Documents',
         'statistics.publicDocuments': 'Public Documents',
         'statistics.privateDocuments': 'Private Documents',
         'statistics.charts': 'Analytical Charts',
         'statistics.documentsByMonth': 'Documents by Month',
         'statistics.documentsByMonthDesc': 'Shows the dynamics of document uploads by month',
         'statistics.userThemes': 'User Themes',
         'statistics.userThemesDesc': 'Distribution of users by selected interface themes',
         'statistics.userLanguages': 'User Languages',
         'statistics.userLanguagesDesc': 'Distribution of users by selected interface languages',
         'statistics.documentVisibility': 'Document Visibility',
         'statistics.documentVisibilityDesc': 'Ratio of public and private documents',
         'statistics.userActivity': 'User Activity',
         'statistics.userActivityDesc': 'Document upload activity by day of week',
         'statistics.download': 'Download',
         'statistics.watermarkInfo': 'Chart Information',
         'statistics.watermarkDesc': 'All charts contain a semi-transparent watermark with the system name and generation date. Charts are created using JFreeChart library and processed with Java Graphics2D API.',
         'statistics.chartTypes': 'Chart types: bar charts, pie charts, line graphs',
         'statistics.watermarkFeatures': 'Watermarks: system name, generation date',
         'statistics.dataSource': 'Data source: PostgreSQL database with fixtures'
      }
    };
    
    this.init();
  }

  init() {
    this.applyLanguage(this.currentLanguage);
    this.bindEvents();
  }

  getStoredLanguage() {
    return localStorage.getItem('language') || 'ru';
  }

  setStoredLanguage(language) {
    localStorage.setItem('language', language);
  }

  applyLanguage(language) {
    if (!this.translations[language]) {
      console.warn(`Language ${language} not supported, falling back to Russian`);
      language = 'ru';
    }
    
    this.currentLanguage = language;
    this.setStoredLanguage(language);
    
    // Update all elements with data-i18n attributes
    this.translatePage();
    
    // Update language selector if it exists
    const languageSelect = document.getElementById('languageSelect');
    if (languageSelect) {
      languageSelect.value = language;
    }
  }

  translatePage() {
    const elements = document.querySelectorAll('[data-i18n]');
    elements.forEach(element => {
      const key = element.getAttribute('data-i18n');
      const translation = this.getTranslation(key);
      if (translation) {
        if (element.tagName === 'INPUT' && element.type === 'text') {
          element.placeholder = translation;
        } else {
          element.textContent = translation;
        }
      }
    });
  }

  getTranslation(key) {
    return this.translations[this.currentLanguage]?.[key] || key;
  }

  switchLanguage(language) {
    if (['ru', 'en'].includes(language)) {
      this.applyLanguage(language);
      this.saveToServer(language);
    }
  }

  async saveToServer(language) {
    try {
      // Try to get current theme from theme selector
      const themeSelect = document.getElementById('themeSelect');
      const currentTheme = themeSelect ? themeSelect.value : 'light';
      
      const response = await fetch('/api/preferences', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: `theme=${currentTheme}&language=${language}`
      });
      
      if (!response.ok) {
        console.warn('Failed to save language preferences to server');
      }
    } catch (error) {
      console.warn('Error saving language preferences:', error);
    }
  }

  bindEvents() {
    // Language switcher dropdown
    const languageSelect = document.getElementById('languageSelect');
    if (languageSelect) {
      languageSelect.addEventListener('change', (e) => {
        this.switchLanguage(e.target.value);
      });
    }

    // Keyboard shortcuts
    document.addEventListener('keydown', (e) => {
      // Ctrl/Cmd + Shift + L to cycle languages
      if ((e.ctrlKey || e.metaKey) && e.shiftKey && e.key === 'L') {
        e.preventDefault();
        this.cycleLanguage();
      }
    });
  }

  cycleLanguage() {
    const languages = ['ru', 'en'];
    const currentIndex = languages.indexOf(this.currentLanguage);
    const nextIndex = (currentIndex + 1) % languages.length;
    this.switchLanguage(languages[nextIndex]);
  }

  // Get current language
  getCurrentLanguage() {
    return this.currentLanguage;
  }

  // Check if language is supported
  isLanguageSupported(language) {
    return ['ru', 'en'].includes(language);
  }

  // Add new translation key-value pair
  addTranslation(language, key, value) {
    if (!this.translations[language]) {
      this.translations[language] = {};
    }
    this.translations[language][key] = value;
  }
}

// Initialize i18n manager when DOM is loaded
document.addEventListener('DOMContentLoaded', () => {
  window.i18nManager = new I18nManager();
});

// Export for use in other scripts
if (typeof module !== 'undefined' && module.exports) {
  module.exports = I18nManager;
}
